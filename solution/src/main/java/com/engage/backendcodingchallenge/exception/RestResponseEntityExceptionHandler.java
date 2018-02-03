package com.engage.backendcodingchallenge.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.engage.backendcodingchallenge.dto.ExceptionDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler(ExpenseIdNotFoundException.class)
    public ResponseEntity<ExceptionDto> expenseIdNotFound(ExpenseIdNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrencyCodeNotSupportedException.class)
    public ResponseEntity<ExceptionDto> currencyCodeNotSupported(CurrencyCodeNotSupportedException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormValidationException.class)
    public ResponseEntity<ExceptionDto> formInvalid(FormValidationException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<ExceptionDto> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error(ex.getMessage());
        return handleExceptionInternal(ex, new ExceptionDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), "HTTP message not valid."), headers, HttpStatus.BAD_REQUEST, request);
    }

}
