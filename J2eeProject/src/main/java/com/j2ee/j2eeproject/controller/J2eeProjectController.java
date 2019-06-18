package com.j2ee.j2eeproject.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.ProductEntity;
import com.j2ee.j2eeproject.entity.pojo.Catalog;
import com.j2ee.j2eeproject.entity.pojo.Product;
import com.j2ee.j2eeproject.entity.pojo.User;
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
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session
				.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		if (listOrders == null) {
			model.addAttribute("quantity", 0);
		} else {
			model.addAttribute("quantity", listOrders.size());
		}
		
		
		List<Product> listTopRatingProduct = Lists.newArrayList(j2eeService.getTopRatingProduct(6));
		List<Product> listLatestProduct = Lists.newArrayList(j2eeService.getListLatestProduct(3));
		Iterable<Catalog> catalogs = j2eeService.getCatalogs();
		model.addAttribute("latestProduct", listLatestProduct.get(0));
		model.addAttribute("ratingProducts", listTopRatingProduct);
		model.addAttribute("hotestProducts", listLatestProduct);
		model.addAttribute("catalogs", catalogs);
		
		return "home-page";
	}

	@GetMapping("/home/products")
	public String showProducts(HttpSession session, Model model) {
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

		Iterable<Product> products = this.j2eeService.getAllProduct();
		model.addAttribute("products", products);
		return "products-list";
	}

	@GetMapping("/home/contact")
	public String contact(Model model) {
		return "contact";
	}

	@GetMapping("/home/products/detail")
	public String showDetail(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
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
		
		Integer productId = Integer.parseInt(request.getParameter("id"));
		ProductEntity product = this.j2eeService.findOneProduct(productId);
		Iterable<Product> relatedProducts = this.j2eeService.selectTopProductByCatalogId(3, product.getCatalogId());
		
		model.addAttribute("product", product);
		model.addAttribute("relatedProducts", relatedProducts);

		return "product-detail";
	}
	
	@GetMapping("/product/search")
	public String search(@RequestParam("term") String term, Model model, HttpSession session) {
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

		Iterable<Product> products = this.j2eeService.getProductByName(term);
		model.addAttribute("products", products);
		return "products-list";
	}
}
