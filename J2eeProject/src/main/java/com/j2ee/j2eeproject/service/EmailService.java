package com.j2ee.j2eeproject.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.constraints.Email;

public interface EmailService {
	void sendEmail(@Email String from, @Email String to, String subject, String content) throws AddressException, MessagingException;
}
