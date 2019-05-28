package com.j2ee.j2eeproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.ProductOrderedEntity;
import com.j2ee.j2eeproject.service.J2eeService;

@Controller
public class OrderController {
	@Autowired
	private J2eeService j2eeService;
	
	@RequestMapping(value = "/home/products/add-product-to-cart", method = RequestMethod.GET)
	public @ResponseBody String addCart(HttpServletRequest request) {
		Integer productId = Integer.parseInt(request.getParameter("productId"));

		HttpSession session = request.getSession();

		List<OrderPreparationEntity> listOrders = this.j2eeService.addToCart(productId, 1, session);

		session.setAttribute(Common.Constaints.kLIST_PRODUCTS, listOrders);

		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(listOrders.size());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}

	@GetMapping(value = {"/detail/descrease-quantity"})
	public @ResponseBody String descreaseQuantity(HttpServletRequest request) {
		String quantityString = request.getParameter("quantity");
		Integer quantity = Integer.parseInt(quantityString);

		quantity = quantity - 1 < 1 ? 1 : --quantity;

		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(quantity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}

	@GetMapping( value = {"/detail/increase-quantity"})
	public @ResponseBody String increaseQuantity(HttpServletRequest request) {
		String quantityString = request.getParameter("quantity");
		Integer quantity = Integer.parseInt(quantityString);

		quantity = quantity + 1 > 100 ? 100 : ++quantity;

		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(quantity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}
	

	@GetMapping("/home/products/detail/add-product-to-cart")
	public @ResponseBody String addToCartInDetail(HttpServletRequest request) {
		String quantityString = request.getParameter("quantity");
		String productString = request.getParameter("productId");
		Integer quantity = Integer.parseInt(quantityString);
		Integer productId = Integer.parseInt(productString);
		HttpSession session = request.getSession();
		
		List<OrderPreparationEntity> listOrders = this.j2eeService.addToCart(productId, quantity, session);

		session.setAttribute(Common.Constaints.kLIST_PRODUCTS, listOrders);

		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(listOrders.size());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ajaxResponse;
	}
	
	@GetMapping("/home/my-cart")
	public String myCart(HttpSession session, Model model) {
		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		Iterable<ProductOrderedEntity> listOrderdEntities = this.j2eeService.getListOrdered(listOrders);
		model.addAttribute("orders", listOrderdEntities);
		return "shopping-cart";
	}
	
	
}
