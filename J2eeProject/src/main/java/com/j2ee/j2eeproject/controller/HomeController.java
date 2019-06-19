package com.j2ee.j2eeproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.pojo.Catalog;
import com.j2ee.j2eeproject.entity.pojo.Product;
import com.j2ee.j2eeproject.entity.pojo.User;
import com.j2ee.j2eeproject.service.J2eeService;

@Controller
public class HomeController {
	@Autowired
	private J2eeService service;

	// =================================================================================
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		model.addAttribute("user", user);

		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session
				.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		if (listOrders == null) {
			model.addAttribute("quantity", 0);
		} else {
			model.addAttribute("quantity", listOrders.size());
		}

		List<Product> listTopRatingProduct = Lists.newArrayList(service.getTopRatingProduct(6));
		List<Product> listLatestProduct = Lists.newArrayList(service.getListLatestProduct(3));
		Iterable<Catalog> catalogs = service.getCatalogs();
		model.addAttribute("latestProduct", listLatestProduct.get(0));
		model.addAttribute("ratingProducts", listTopRatingProduct);
		model.addAttribute("hotestProducts", listLatestProduct);
		model.addAttribute("catalogs", catalogs);

		return "home-page";
	}

	@GetMapping("/home/category")
	public String showCategory(HttpSession session, Model model, @RequestParam("catalogId") int catalog_id) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		model.addAttribute("user", user);

		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session
				.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		if (listOrders == null) {
			model.addAttribute("quantity", 0);
		} else {
			model.addAttribute("quantity", listOrders.size());
		}

		Iterable<Product> products = this.service.getProductByCatalogId(catalog_id);
		model.addAttribute("products", products);
		return "products-list";
	}
}
