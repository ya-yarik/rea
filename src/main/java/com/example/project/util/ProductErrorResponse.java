package com.example.project.util;

public class ProductErrorResponse {
    private String message;

    public ProductErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}