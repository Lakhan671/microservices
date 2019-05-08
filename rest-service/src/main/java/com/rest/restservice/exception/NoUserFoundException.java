package com.rest.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserFoundException extends RuntimeException {


	public NoUserFoundException(String arg0) {
		super(arg0);
	
	}

}
