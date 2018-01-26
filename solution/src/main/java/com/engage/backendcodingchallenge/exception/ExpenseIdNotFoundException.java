package com.engage.backendcodingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if there was an unsuccessful lookup for an expense.
 * @author patriciaespada
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExpenseIdNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3368535233879694176L;

    public ExpenseIdNotFoundException(String message) {
        super(message);
    }

}
