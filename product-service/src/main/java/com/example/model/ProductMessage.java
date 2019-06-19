package com.example.model;

/**
 * @author Dharmendra Pandit.
 */
public enum ProductMessage {

    AUTHENTICATION_FAILED("Authentication is failed"),
    PRODUCT_MANDATORY_FIELDS("productName/quantity/price/model may be missing");

    private String message;

    ProductMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
