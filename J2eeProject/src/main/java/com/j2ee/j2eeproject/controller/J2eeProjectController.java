package com.j2ee.j2eeproject.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.j2ee.j2eeproject.entity.Product;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.service.J2eeService;
import com.j2ee.j2eeproject.validation.EmailExistsException;
import com.j2ee.j2eeproject.validation.LoginException;

@Controller
public class J2eeProjectController {
	@Autowired
	private J2eeService j2eeService;

	@RequestMapping(value = { "/", "/login" })
	public String login() {
		return "login-ver2";
	}

	@RequestMapping("/login-google")
	public String loginGoogle(HttpServletRequest request, Model model) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}

		try {
			User user = this.j2eeService.loginWithGoogle(code);
			model.addAttribute("user", user);
			return "signup";
		} catch (EmailExistsException e) {
			return "redirect:/home";
		}
	}

	@GetMapping("/home")
	public String home(Model model) {
		Iterable<Product> products = this.j2eeService.getAllProduct();
		
		//List<ImageSample> listSamples = this.j2eeService.searchImageFromProductId(productId)
		model.addAttribute("products", products);
		
		return "food-list";
	}

	@PostMapping(value = "/signup")
	public String signup(@Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}

		j2eeService.saveUser(user);
		model.addAttribute("user", user);
		return "redirect:/home";
	}

	@PostMapping(value = "/login-manually")
	public String loginManually(@Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		try {
			User validUser = j2eeService.login(user);
			model.addAttribute("user", validUser);
			return "redirect:/home";
		} catch (LoginException e) {
			//Add error message
			redirect.addFlashAttribute("Error", e.getMessage());
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/login/forgot-password") 
	public String forgotPassword() {
		return "forgot-password";
	}

	@RequestMapping(value = "/login/resetpassword", method = RequestMethod.POST) 
	public String resetPassword(Model model) {
		return "reset-password";
	}
	
	@PostMapping("/testurl")
	public String testUrl() {
		return "user";
	}
}
