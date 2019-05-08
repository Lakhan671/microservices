package com.rest.restservice.exception;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * @author Lakhan Singh(S786561)
 * @since 3.0
 * @Date  28-11-2018
 *
 */
public class ASCApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ASCApiError() {
        super();
    }

    public ASCApiError(final HttpStatus status, final String message, final List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ASCApiError(final HttpStatus status, final String message, final String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    public void setError(final String error) {
        errors = Arrays.asList(error);
    }

}
