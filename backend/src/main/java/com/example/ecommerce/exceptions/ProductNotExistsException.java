package com.example.ecommerce.exceptions;

public class ProductNotExistsException extends Exception {
    public ProductNotExistsException(String msg) {
        super(msg);
    }
}
