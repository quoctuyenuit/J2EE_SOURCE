package com.j2ee.j2eeproject.controller;

import java.util.LinkedHashMap;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.j2ee.j2eeproject.entity.User;


@Controller
public class J2eeProjectController {
	@GetMapping("/loginform")
	public String login() {
		return "login-ver2";
	}
	
	@RequestMapping(value = "/auth/google")
	public String loginSucessfully(OAuth2Authentication authentication, Model model) {
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication
				.getUserAuthentication().getDetails();

		User user = new User();
		user.setEmail(properties.get("email").toString());
		user.setName(properties.get("name").toString());
		user.setId(properties.get("id").toString());

		model.addAttribute("user", user);
		return "signup";

	}
}
