package com.j2ee.j2eeproject.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.j2ee.j2eeproject.common.AccountExists;
import com.j2ee.j2eeproject.common.Common;
import com.j2ee.j2eeproject.common.LocalizeStrings;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.ProductEntity;
import com.j2ee.j2eeproject.entity.pojo.GooglePojo;
import com.j2ee.j2eeproject.entity.pojo.ProductImage;
import com.j2ee.j2eeproject.entity.pojo.Product;
import com.j2ee.j2eeproject.entity.pojo.User;
import com.j2ee.j2eeproject.entity.pojo.UserType;
import com.j2ee.j2eeproject.repository.ProductImageRepository;
import com.j2ee.j2eeproject.repository.ProductRepository;
import com.j2ee.j2eeproject.repository.UserRepository;
import com.j2ee.j2eeproject.repository.UserTypeRepository;
import com.j2ee.j2eeproject.untils.GoogleUtils;
import com.j2ee.j2eeproject.validation.EmailExistsException;
import com.j2ee.j2eeproject.validation.LoginException;
import com.j2ee.j2eeproject.validation.ResetPasswordException;
import java.util.stream.*;

@Service
public class J2eeServiceImpl implements J2eeService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private GoogleUtils googleUtils;

	@Override
	public User searchUsers(String email) {
		List<User> listUsers = userRepository.findByEmailContaining(email);
		if (listUsers.size() == 0) {
			return null;
		} else {
			return listUsers.get(0);
		}
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
	public List<ProductImage> searchImageFromProductId(Integer productId) {
		return productImageRepository.findByProductId(productId);
	}

	@Override
	public Iterable<Product> getAllProduct() {
		return productRepository.findAll();
	}
	
	@Override
	public ProductEntity findOneProduct(Integer id) {
		// TODO Auto-generated method stub
		Product productPojo =  productRepository.findById(id).get();
		ProductEntity entity = new ProductEntity(productPojo.getId(), productPojo.getName(), productPojo.getPrice(), productPojo.getDiscount(), productPojo.getDescription());
		List<String> listProductImages = productImageRepository.findByProductId(productPojo.getId()).stream().map(image -> {
			return image.getName();
		}).collect(Collectors.toCollection(ArrayList::new));
		listProductImages.add(productPojo.getImageSample());
		entity.setListImages(listProductImages);
		return entity;
	}
	
	@Override
	public List<OrderPreparationEntity> addToCart(Integer productId, Integer quantity, HttpSession session) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<OrderPreparationEntity> listOrders = (List<OrderPreparationEntity>) session.getAttribute(Common.Constaints.kLIST_PRODUCTS);
		
		if (listOrders == null) {
			listOrders = new ArrayList<OrderPreparationEntity>();
		} else {
			for (OrderPreparationEntity order : listOrders) {
				if (order.getProductId() == productId) {
					order.setQuantity(order.getQuantity() + quantity);
					return listOrders;
				}
			}
		}
		
		listOrders.add(new OrderPreparationEntity(productId, quantity));
		return listOrders;
	}

	// ----------------------------------------------------------------
	// Login - Sign
	// ----------------------------------------------------------------

	@Override
	public org.javatuples.Pair<User, AccountExists> loginWithGoogle(String code) throws ClientProtocolException, IOException {

		String accessToken = googleUtils.getToken(code);
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);

		AccountExists accountExists = AccountExists.NotExists;
		if (this.searchUsers(googlePojo.getEmail()) != null) {
			accountExists = AccountExists.Exists;
		}

		UserType userType = this.searchUserTypes(Common.Constaints.USER_PERMISSION);
		User user = new User(googlePojo.getId(), userType.getId());
		user.setEmail(googlePojo.getEmail());
		user.setName(googlePojo.getName());

		return new org.javatuples.Pair<User, AccountExists>(user, accountExists);

	}

	@Override
	public User login(User user) throws LoginException {
		User dataUser = this.searchUsers(user.getEmail());
		if (dataUser != null) {
			if (user.getPassword().equals(dataUser.getPassword()))
				return dataUser;
		}
		throw new LoginException(LocalizeStrings.getInstance().account_or_password_is_incorrect);
	}

	@Override
	public String sendVerificationCode(@Email String email) throws Throwable {
		User currentUser = this.searchUsers(email);
		if (currentUser == null) {
			throw new EmailExistsException(LocalizeStrings.getInstance().email_is_not_exists);
		}
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 5; i++) {
			if (i % 2 == 0)
				code += (char) (rand.nextInt('Z' - 'A') + 'A');
			else
				code += rand.nextInt(9);
		}
		String msg = "Your verification code is " + code;
		emailService.sendEmail("quoctuyen9aht@gmail.com", email, "J2ee Verification", msg);
		return code;
	}

	@Override
	public String resetPassword(String email, String verifyCodeSent, String verifyCode, String password,
			String passwordAgain) throws ResetPasswordException {
		if (!verifyCodeSent.equals(verifyCode)) {
			throw new ResetPasswordException(LocalizeStrings.getInstance().wrong_verifcation_code);
		}

		if (!password.equals(passwordAgain)) {
			throw new ResetPasswordException(LocalizeStrings.getInstance().confirm_password_is_not_match);
		}

		User currentUser = this.searchUsers(email);
		if (currentUser != null) {
			
			currentUser.setPassword(password);
			this.saveUser(currentUser);
			return LocalizeStrings.getInstance().reset_password_successful;
		}

		throw new ResetPasswordException(LocalizeStrings.getInstance().email_is_not_exists);
	}

}
