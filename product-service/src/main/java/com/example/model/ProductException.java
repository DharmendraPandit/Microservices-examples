package com.example.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dharmendra Pandit.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ProductException extends RuntimeException {

    private final int status;
    private final String message;
    private String errorCode;

    public ProductException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ProductException(int status, String message, String errorCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
