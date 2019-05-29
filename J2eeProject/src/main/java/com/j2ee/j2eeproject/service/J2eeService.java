package com.j2ee.j2eeproject.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

import org.apache.http.client.ClientProtocolException;
import org.javatuples.Pair;

import com.j2ee.j2eeproject.common.AccountExists;
import com.j2ee.j2eeproject.entity.OrderPreparationEntity;
import com.j2ee.j2eeproject.entity.ProductEntity;
import com.j2ee.j2eeproject.entity.ProductOrderedEntity;
import com.j2ee.j2eeproject.entity.pojo.ProductImage;
import com.j2ee.j2eeproject.entity.pojo.Product;
import com.j2ee.j2eeproject.entity.pojo.User;
import com.j2ee.j2eeproject.entity.pojo.UserType;
import com.j2ee.j2eeproject.validation.LoginException;
import com.j2ee.j2eeproject.validation.ResetPasswordException;

public interface J2eeService {

	User searchUsers(String email);

	void saveUser(User user);

	Optional<User> findOneUser(String id);

	UserType searchUserTypes(String name);

	List<ProductImage> searchImageFromProductId(Integer productId);

	Iterable<Product> getAllProduct();
	
	Iterable<Product> selectTopProductByCatalogId(int limitNumber, int catalogId);

	ProductEntity findOneProduct(Integer id);
	
	Iterable<ProductOrderedEntity> getListOrdered(List<OrderPreparationEntity> listOrders);
	
	Iterable<Product> getListLatestProduct(int limitedNumber);
	
	Iterable<Product> getTopRatingProduct(int limitedNumber);

	//-------------------------------------------------------------------------------------------------------------------
	List<OrderPreparationEntity> addToCart(Integer productId, Integer quantity, HttpSession session);
	//-------------------------------------------------------------------------------------------------------------------
	Pair<User, AccountExists> loginWithGoogle(String code) throws ClientProtocolException, IOException;

	User login(User user) throws LoginException;

	String sendVerificationCode(@Email String email) throws Throwable;

	String resetPassword(String email, String verifyCodeSent, String verifyCode, String password, String passwordAgain)
			throws ResetPasswordException;
}
