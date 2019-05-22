package com.j2ee.j2eeproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2ee.j2eeproject.common.AccountExists;
import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.entity.TakenOrder;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.Product;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.service.J2eeService;
import com.j2ee.j2eeproject.validation.LoginException;
import com.j2ee.j2eeproject.validation.ResetPasswordException;

@Controller
public class J2eeProjectController {
	@Autowired
	private J2eeService j2eeService;

	@RequestMapping(value = { "/login" })
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			return "redirect:/home";
		} else {
			return "login-ver2";
		}
	}

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request, Model model) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		Pair<User, AccountExists> loginResult = this.j2eeService.loginWithGoogle(code);
		model.addAttribute("user", loginResult.getValue0());

		if (loginResult.getValue1() == AccountExists.NotExists) {
			return "signup";
		} else {
			HttpSession session = request.getSession();
			User user = this.j2eeService.searchUsers(loginResult.getValue0().getEmail());
			session.setAttribute(Common.Constaints.kUSER, user);
			return "redirect:/home";
		}
	}

	@PostMapping(value = "/signup")
	public String signup(HttpServletRequest request, @Valid User user, BindingResult result,
			RedirectAttributes redirect, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}

		j2eeService.saveUser(user);
		model.addAttribute("user", user);
		HttpSession session = request.getSession();
		session.setAttribute(Common.Constaints.kUSER, user);
		return "redirect:/home";
	}

	@PostMapping(value = "/login-manually")
	public String loginManually(HttpServletRequest request, @Valid User user, BindingResult result,
			RedirectAttributes redirect, Model model) {
		try {
			User validUser = j2eeService.login(user);
			request.getSession().setAttribute("user", validUser);
			return "redirect:/home";
		} catch (LoginException e) {
			// Add error message
			redirect.addFlashAttribute("error", e.getMessage());
			return "redirect:/login?message=fail-to-login";
		}
	}

	@RequestMapping(value = "/login/forgot-password")
	public String forgotPassword() {
		return "forgot-password";
	}

	@RequestMapping(value = "/login/resetpassword-processing", method = RequestMethod.GET)
	public String resetPassword(HttpServletRequest request, @RequestParam("email") String email,
			RedirectAttributes redirect, Model model) {
		try {
			HttpSession session = request.getSession();
			String verifyCode = this.j2eeService.sendVerificationCode(email);
			session.setAttribute("verifyCode", verifyCode);
			session.setAttribute("email", email);
			return "redirect:/login/resetpassword";
		} catch (Throwable e) {
			redirect.addFlashAttribute("error", e.getMessage());
			return "redirect:/login/forgot-password?message=could-not-send-verifycode";
		}
	}

	@GetMapping("/login/resetpassword")
	public String resetPasswordPage() {
		return "reset-password";
	}

	@RequestMapping(value = "/login/resetpassword/verify", method = RequestMethod.POST)
	public String doResetPassword(HttpServletRequest request, @RequestParam("verifycode") String verifyCode,
			@RequestParam("password") String password, @RequestParam("confirmpassword") String confirmPassword,
			RedirectAttributes redirect) {
		HttpSession session = request.getSession();
		String email = session.getAttribute("email").toString();
		String verifyCodeSent = session.getAttribute("verifyCode").toString();

		try {
			String msgString = this.j2eeService.resetPassword(email, verifyCodeSent, verifyCode, password,
					confirmPassword);
			redirect.addFlashAttribute("messageSuccess", msgString);
			return "redirect:/login";
		} catch (ResetPasswordException e) {
			redirect.addFlashAttribute("error", e.getMessage());
			return "redirect:/login/resetpassword?message=error";
		}
	}

	// =================================================================================
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Common.Constaints.kUSER);
		List<TakenOrder> listOrders = new ArrayList<TakenOrder>();
		if (user != null) {
			listOrders = j2eeService.searchOrderByUserId(user.getId());

		}

		model.addAttribute("listOrders", listOrders);
		model.addAttribute("user", user);
		return "home-page";
	}

	@GetMapping("/home/products")
	public String showProducts(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		
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
