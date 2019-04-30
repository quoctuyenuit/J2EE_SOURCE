package com.j2ee.j2eeproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.j2ee.j2eeproject.entity.User;

@Controller
public class J2eeProjectController {
	@GetMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("user", new User());
		return "loginform";
	}
}
