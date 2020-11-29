package br.com.stoom.qualification.controller;

import br.com.stoom.qualification.exception.AddressNotFoundException;
import br.com.stoom.qualification.exception.GeocodingException;
import br.com.stoom.qualification.model.ErrorResponse;
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

    @ExceptionHandler(value = {AddressNotFoundException.class, GeocodingException.class})
    protected ResponseEntity<Object> addressNotFound(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, new ErrorResponse(exception.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
}
