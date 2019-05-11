package com.j2ee.j2eeproject.validation;

public class EmailExistsException extends Throwable {
	public EmailExistsException(final String message) {
        super(message);
    }
}
