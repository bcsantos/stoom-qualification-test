package br.com.stoom.qualification.controller;

import br.com.stoom.qualification.exception.AddressNotFoundException;
import br.com.stoom.qualification.exception.GeocodingException;
import br.com.stoom.qualification.model.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> invalidId(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorResponse("Invalid id format!"), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = {AddressNotFoundException.class, GeocodingException.class, EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> notFound(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorResponse(exception.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> constraintViolation(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorResponse("All required fields must be informed: [streetName, number, neighbourhood, city, state, country, zipcode]"), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = {PSQLException.class})
    protected ResponseEntity<Object> dataIntegrity(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorResponse("An invalid field value was informed."), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    protected ResponseEntity<Object> invalidFieldValue(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorResponse("Invalid field name passed in input query. Valid (case-sensitive) values are: [streetName, number, complement, neighbourhood, city, state, country, zipcode, latitude, longitude]."), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
}
