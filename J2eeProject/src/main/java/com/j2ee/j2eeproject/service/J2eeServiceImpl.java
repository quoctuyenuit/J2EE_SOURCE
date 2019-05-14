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
import com.j2ee.j2eeproject.entity.ImageSample;
import com.j2ee.j2eeproject.entity.Product;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.entity.UserType;
import com.j2ee.j2eeproject.repository.ImageSampleRepository;
import com.j2ee.j2eeproject.repository.ProductRepository;
import com.j2ee.j2eeproject.repository.UserRepository;
import com.j2ee.j2eeproject.repository.UserTypeRepository;
import com.j2ee.j2eeproject.untils.GoogleUtils;
import com.j2ee.j2eeproject.validation.EmailExistsException;
import com.j2ee.j2eeproject.validation.LoginException;

@Service
public class J2eeServiceImpl implements J2eeService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ImageSampleRepository imageSampleRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private GoogleUtils googleUtils;

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

	@Override
	public UserType searchUserTypes(String name) {
		List<UserType> listResult = userTypeRepository.findByNameContaining(name);
		if (listResult.size() > 0)
			return listResult.get(0);
		return null;
	}
	
	@Override
	public List<ImageSample> searchImageFromProductId(Integer productId) {
		return imageSampleRepository.findByProductIdContaining(productId);
	}
	
	@Override
	public Iterable<Product> getAllProduct() {
		return productRepository.findAll();
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

		UserType userType = this.searchUserTypes(Common.Constaints.USER_PERMISSION);
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
