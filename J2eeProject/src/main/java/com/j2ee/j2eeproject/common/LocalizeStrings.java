package com.j2ee.j2eeproject.common;

public class LocalizeStrings {
	private static LocalizeStrings _instance;
	
	//
	public static LocalizeStrings getInstance() {
		if (_instance == null) 
		{
			_instance = new LocalizeStrings();
		}
		return _instance;
	}

	public String account_or_password_is_incorrect = "Account or password is incorrect!";
	
	public String could_not_send_verification_code = "Something is error, we could not send verification code for you!";
	
	public String wrong_verifcation_code = "Verification code is incorrect";
	
	public String confirm_password_is_not_match = "Comfirm password and password is not match!";
	
	public String reset_password_successful = "Reset password successful, you can login now";
	
	public String email_is_not_exists = "Your email was not registed in system";
}
