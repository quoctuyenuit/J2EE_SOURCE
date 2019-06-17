package com.j2ee.j2eeproject.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.MultipartBodyBuilder.PublisherEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.ProductOrderedEntity;
import com.j2ee.j2eeproject.entity.pojo.OrderDetail;
import com.j2ee.j2eeproject.entity.pojo.RequestOrder;
import com.j2ee.j2eeproject.entity.pojo.User;
import com.j2ee.j2eeproject.service.J2eeService;

@Controller
public class MyCartController {
	class ProductPrice {
		private Integer quantity;
		private String totalPrice;
		private String subtotal;
		private String totalListPrice;

		public String getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Integer totalPrice) {
			this.totalPrice = Common.formatMoney(totalPrice);
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public void setTotalListPrice(Integer totalListPrice) {
			this.totalListPrice = Common.formatMoney(totalListPrice);
		}

		public String getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(Integer subtotal) {
			this.subtotal = Common.formatMoney(subtotal);
		}
		
		public ProductPrice(Integer quantity, Integer totalPrice, Integer subtotal, Integer totalListPrice) {
			super();
			this.quantity = quantity;
			this.setTotalPrice(totalPrice);
			this.setTotalListPrice(totalListPrice);
			this.setSubtotal(subtotal);
		}

		public String getTotalListPrice() {
			return totalListPrice;
		}
	}

	@Autowired
	private J2eeService service;

	@GetMapping(value = { "/my-cart/increase-quantity" })
	public @ResponseBody String increaseQuantity(HttpServletRequest request) {
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		Integer price = Integer.parseInt(request.getParameter("price"));
		Integer productId = Integer.parseInt(request.getParameter("productId"));

		// =================================================================================
		// Modify data in session
		HttpSession session = request.getSession();
		List<OrderPreparationEntity> listOrders = service.addToCart(productId, 1, session);
		session.setAttribute(Common.Constaints.kLIST_PRODUCTS, listOrders);
		// =================================================================================
		++quantity;
		Integer totalPrice = price * quantity;
		
		
		Integer totalListPrice = 0; 
		for (ProductOrderedEntity order : this.service.getListOrdered(listOrders)) {
			totalListPrice += order.getPrice() * order.getQuantity();
		}
		ProductPrice productPrice = new ProductPrice(quantity, totalPrice, totalListPrice, totalListPrice);
		// =================================================================================
		// Write to json response data
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(productPrice);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}

	@GetMapping(value = { "/my-cart/descrease-quantity" })
	public @ResponseBody String descreaseQuantity(HttpServletRequest request) {
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		Integer price = Integer.parseInt(request.getParameter("price"));
		Integer productId = Integer.parseInt(request.getParameter("productId"));

		// =================================================================================
		// Modify data in session
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		
		if (quantity > 1) {
			listOrders = service.addToCart(productId, -1, session);
			session.setAttribute(Common.Constaints.kLIST_PRODUCTS, listOrders);
		}
		// =================================================================================
		quantity = quantity - 1 < 1 ? 1 : --quantity;
		Integer totalPrice = price * quantity;
		Integer totalListPrice = 0; 
		
		for (ProductOrderedEntity order : this.service.getListOrdered(listOrders)) {
			totalListPrice += order.getPrice() * order.getQuantity();
		}
		ProductPrice productPrice = new ProductPrice(quantity, totalPrice, totalListPrice, totalListPrice);
		// =================================================================================
		// Write to json response data
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(productPrice);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}
	
	@GetMapping("/my-cart/delete") 
	public String deleteCart(HttpSession session, @RequestParam("productId") Integer productId) {
		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		listOrders.removeIf(order -> order.getProductId() == productId);
		
		return "redirect:/home/my-cart";
	}
	
	@GetMapping("/my-cart/checkout-processing")
	public String checkoutProcessing(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		if (user == null) {
			return "redirect:/login";
		} else {
			return "redirect:/my-cart/checkout";	
		}
	}
	
	@RequestMapping(value = "/my-cart/checkout")
	public String checkout(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		model.addAttribute("user", user);
		
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		Iterable<ProductOrderedEntity> listOrderdEntities = this.service.getListOrdered(listOrders);
		model.addAttribute("products", listOrderdEntities);
		model.addAttribute("shippingCharge", 10000);
		return "checkout";
	}
	
	@GetMapping("/checkout-action")
	public @ResponseBody String checkoutAction(HttpServletRequest request, @Valid User user, HttpSession session) {
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		this.service.saveUser(user);
		int id = this.service.saveOrder(user.getId());
		listOrders.forEach(product -> {
			OrderDetail orderDetail = new OrderDetail();
			OrderDetail.PrimaryKey pKey  = new OrderDetail.PrimaryKey(product.getProductId(), id);
			orderDetail.setPk(pKey);
			this.service.saveOrderDetail(orderDetail);
		});
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(id);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		session.setAttribute(Common.Constaints.kLIST_PRODUCTS, null);

		return ajaxResponse;
	}
	
	@GetMapping("/home/checkout-successful")
	public String checkoutSuccess() {
		return "success-payment";
	}
}
