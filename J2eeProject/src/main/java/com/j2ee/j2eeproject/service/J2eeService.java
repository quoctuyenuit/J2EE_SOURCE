package com.j2ee.j2eeproject.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;

import com.j2ee.j2eeproject.entity.ImageSample;
import com.j2ee.j2eeproject.entity.Product;
import com.j2ee.j2eeproject.entity.User;
import com.j2ee.j2eeproject.entity.UserType;
import com.j2ee.j2eeproject.validation.EmailExistsException;
import com.j2ee.j2eeproject.validation.LoginException;

public interface J2eeService {

    List<User> searchUsers(String email);

    void saveUser(User user);
    
    Optional<User> findOneUser(String id);
    
    UserType searchUserTypes(String name);
    
    List<ImageSample> searchImageFromProductId(Integer productId);
    
    Iterable<Product> getAllProduct();

    User loginWithGoogle(String code) throws EmailExistsException, ClientProtocolException, IOException;

    User login(User user) throws LoginException;
    
    String sendVerificationCode() throws Throwable;
    
    
}