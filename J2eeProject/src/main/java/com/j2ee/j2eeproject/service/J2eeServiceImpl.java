package com.j2ee.j2eeproject.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.common.LocalizeStrings;
import com.j2ee.j2eeproject.entity.GooglePojo;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.entity.User_Type;
import com.j2ee.j2eeproject.repository.UserRepository;
import com.j2ee.j2eeproject.repository.UserTypeRepository;
import com.j2ee.j2eeproject.untils.GoogleUtils;
import com.j2ee.j2eeproject.validation.EmailExistsException;
import com.j2ee.j2eeproject.validation.LoginException;
import com.sun.mail.handlers.image_gif;

@Service
public class J2eeServiceImpl implements J2eeService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private GoogleUtils googleUtils;

	private static class Constaints {
		public static String CharacterCollection = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}
	// ----------------------------------------------------------------
	// User
	// ----------------------------------------------------------------
	@Override
	public List<User> searchUsers(String email) {
		return userRepository.findByEmailContaining(email);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public Optional<User> findOneUser(String id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	// ----------------------------------------------------------------
	// User Type
	// ----------------------------------------------------------------

	@Override
	public User_Type searchUser_Types(String name) {
		List<User_Type> listResult = userTypeRepository.findByNameContaining(name);
		if (listResult.size() > 0)
			return listResult.get(0);
		return null;
	}

	// ----------------------------------------------------------------
	// Login - Sign
	// ----------------------------------------------------------------

	@Override
	public User loginWithGoogle(String code) throws EmailExistsException, ClientProtocolException, IOException {

		String accessToken = googleUtils.getToken(code);
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		if (this.searchUsers(googlePojo.getEmail()).size() > 0) {
			throw new EmailExistsException("There is an account with that email adress:" + googlePojo.getEmail());
		}

		User_Type userType = this.searchUser_Types(Common.Constaints.USER_PERMISSION);
		User user = new User(googlePojo.getId(), userType.getId());
		user.setEmail(googlePojo.getEmail());
		user.setName(googlePojo.getName());

		return user;

	}

	@Override
	public User login(User user) throws LoginException {
		List<User> listUser = this.searchUsers(user.getEmail());
		if (listUser.size() > 0) {
			User dataUser = listUser.get(0);
			if (user.getPassword().equals(dataUser.getPassword()))
				return user;
		}
		throw new LoginException(LocalizeStrings.account_or_password_is_incorrect);
	}

	@Override
	public String sendVerificationCode() throws Throwable {
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 5; i++) {
			if (i % 2 == 0)
				code += (char)(rand.nextInt('Z' - 'A') + 'A');
			else 
				code += rand.nextInt(9);
		}
		String msg = "Your verification code is " + code;
		emailService.sendEmail("quoctuyen.uit@gmail.com", "quoctuyen9aht@gmail.com", "J2ee Verification", msg);
		return code;
	}
}
