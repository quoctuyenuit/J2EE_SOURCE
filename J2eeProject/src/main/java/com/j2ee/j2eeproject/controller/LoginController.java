package com.j2ee.j2eeproject.controller;

import java.util.LinkedHashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@RequestMapping(value = "/auth/google")
	public Authentication user(OAuth2Authentication authentication) {
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication
				.getUserAuthentication().getDetails();
		
		
		
		String name = (String) properties.get("name");
		
		
		
		return (Authentication) properties.get("email");
	}
}
