package com.j2ee.j2eeproject.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.j2ee.j2eeproject.common.LocalizeStrings;
import com.j2ee.j2eeproject.entity.Product;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.service.J2eeService;
import com.j2ee.j2eeproject.validation.EmailExistsException;
import com.j2ee.j2eeproject.validation.LoginException;
import com.j2ee.j2eeproject.validation.ResetPasswordException;

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

	@PostMapping("/testurl")
	public String testUrl() {
		return "user";
	}
}
