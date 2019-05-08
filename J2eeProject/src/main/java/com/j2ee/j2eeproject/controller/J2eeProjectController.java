package com.j2ee.j2eeproject.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.j2ee.j2eeproject.entity.GooglePojo;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.service.UserService;
import com.j2ee.j2eeproject.untils.GoogleUtils;

@Controller
public class J2eeProjectController {
	@Autowired
	private UserService userService;
	@Autowired
	private GoogleUtils googleUtils;

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
		String accessToken = googleUtils.getToken(code);

		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);

		User user = new User();

		user.setEmail(googlePojo.getEmail());
		user.setName(googlePojo.getName());
		user.setId(googlePojo.getId());
		model.addAttribute("user", user);
		
		if (userService.search(user.getEmail()).size() == 0)
		{
			model.addAttribute("user", user);
			return "signup";
		}
		
		return "redirect:/home";
	}


	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@PostMapping(value = "/signup")
	public String signup(@Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}

		userService.save(user);
		model.addAttribute("user", user);
		return "redirect:/home";
	}
	
	@PostMapping("/testurl")
	public String testUrl()
	{
		return "user";
	}
}
