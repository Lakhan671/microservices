package com.rest.restservice.exception;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * A convenient base class for
 * {@link ASCExceptionHandlerController @ControllerAdvice} classes that wish to
 * provide custom exception exception handling across all methods.
 *
 * <p>
 * This base class provides methods for handling internal Spring MVC
 * exceptions. This method returns a {@code ResponseEntity}
 * <p>
 * There is no need to write error content to the response body.
 *
 * @author Lakhan Singh
 * @Date 28-11-2018
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 * @since 3.0
 */

//@ControllerAdvice
public class ASCExceptionHandlerController extends ResponseEntityExceptionHandler {

    //400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        errors.addAll(ex.getBindingResult().getFieldErrors().stream().map(er -> (er.getField() + " : " + er.getDefaultMessage())).collect(Collectors.toList()));
        errors.addAll(ex.getBindingResult().getGlobalErrors().stream().map(error -> (error.getObjectName() + ": " + error.getDefaultMessage())).collect(Collectors.toList()));
        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, ascApiError, headers, ascApiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        errors.addAll(ex.getBindingResult().getFieldErrors().stream().map(error -> (error.getField() + ": " + error.getDefaultMessage())).collect(Collectors.toList()));
        errors.addAll(ex.getBindingResult().getGlobalErrors().stream().map(error -> (error.getObjectName() + ": " + error.getDefaultMessage())).collect(Collectors.toList()));
        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, ascApiError, headers, ascApiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMsg = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorMsg);
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMsg = ex.getRequestPartName() + " part is missing";
        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorMsg);
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMsg = ex.getParameterName() + " parameter is missing";
        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorMsg);
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    //

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        String typeName = (null != ex.getRequiredType()&&ex.getRequiredType().getName()!=null) ? ex.getRequiredType().getName() : "NA";
        final String errorMsg = ex.getName() + " should be of type " + typeName;

        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorMsg);
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        errors.addAll(ex.getConstraintViolations().stream().map(violation -> (violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage())).collect(Collectors.toList()));

        final ASCApiError ascApiError = new ASCApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMsg = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ASCApiError ascApiError = new ASCApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errorMsg);
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        if (null!=ex.getSupportedHttpMethods() && !ex.getSupportedHttpMethods().isEmpty()) {
            ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        }

        final ASCApiError ascApiError = new ASCApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final ASCApiError ascApiError = new ASCApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

    // 500

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        final String errorMsg = "Internal server error";
        final ASCApiError ascApiError = new ASCApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg + ":" + ex.getLocalizedMessage(),"INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(ascApiError, new HttpHeaders(), ascApiError.getStatus());
    }

}
