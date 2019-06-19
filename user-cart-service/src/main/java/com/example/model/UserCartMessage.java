package com.example.model;

/**
 * @author Dharmendra Pandit.
 */
public enum UserCartMessage {

    USER_INVALID("user id or password is invalid");

    private String message;

    UserCartMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
