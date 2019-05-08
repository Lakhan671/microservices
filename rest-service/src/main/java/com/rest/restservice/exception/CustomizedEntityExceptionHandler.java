package com.rest.restservice.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
//@RestController
public class CustomizedEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({Exception.class})
public final ResponseEntity<Object>HandelException(Exception ex,WebRequest re){
	ExceptionResponse response=new ExceptionResponse(new Date(),ex.getMessage(), re.getDescription(false));
	
	return new ResponseEntity<Object>(response,HttpStatus.INTERNAL_SERVER_ERROR);

}
	
	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse response=new ExceptionResponse(new Date(),ex.getMessage(), ex.getBindingResult().toString());
		
		return new ResponseEntity<Object>(response,status.BAD_REQUEST);
	}
}
