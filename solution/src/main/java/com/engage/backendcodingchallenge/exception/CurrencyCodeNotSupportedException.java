package com.engage.backendcodingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if there was the provided currency code is not valid.
 * @author patriciaespada
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CurrencyCodeNotSupportedException extends RuntimeException {
    
    private static final long serialVersionUID = -5504809358161408795L;

    public CurrencyCodeNotSupportedException(String message) {
        super(message);
    }

}
