package com.j2ee.j2eeproject.controller;

import java.util.List;
import java.util.Optional;

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
import com.j2ee.j2eeproject.entity.Product;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.service.J2eeService;

@Controller
public class J2eeProjectController {
	@Autowired
	private J2eeService j2eeService;
	// =================================================================================
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		model.addAttribute("user", user);
		
		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		if (listOrders == null) {
			model.addAttribute("quantity", 0);
		} else {
			model.addAttribute("quantity", listOrders.size());
		}
		return "home-page";
	}

	@GetMapping("/home/products")
	public String showProducts(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		model.addAttribute("user", user);
		
		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		if (listOrders == null) {
			model.addAttribute("quantity", 0);
		} else {
			model.addAttribute("quantity", listOrders.size());
		}
		
		Iterable<Product> products = this.j2eeService.getAllProduct();
		model.addAttribute("products", products);
		return "food-list";
	}
	
	@GetMapping("/home/contact")
	public String contact(Model model) {
		return "contact";
	}

	@GetMapping("/home/products/detail")
	public String showDetail(HttpServletRequest request, Model model) {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		Optional<Product> product = this.j2eeService.findOneProduct(productId);

		model.addAttribute("product", product.get());
		return "food-detail";
	}

	@RequestMapping(value = "/home/products/add-product-to-cart", method = RequestMethod.GET)
	public @ResponseBody String addCart(HttpServletRequest request) {
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		
		HttpSession session = request.getSession();
		
		List<OrderPreparationEntity> listOrders = this.j2eeService.addToCart(productId, session);
		
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
}
