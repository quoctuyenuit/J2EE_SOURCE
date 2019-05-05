package com.j2ee.j2eeproject.controller;

import java.util.LinkedHashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.j2ee.j2eeproject.entity.User;


@Controller
public class J2eeProjectController {
	@GetMapping("/loginform")
	public String hello(Model model) {
		model.addAttribute("user", new User());
		return "loginform";
	}
}
