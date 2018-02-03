package com.engage.backendcodingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if there was errors in form request.
 * @author patriciaespada
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FormValidationException extends RuntimeException {

    private static final long serialVersionUID = -4529973834192848795L;

    public FormValidationException(String message) {
        super(message);
    }

}
