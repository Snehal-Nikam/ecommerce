package com.example.ecommerce.exceptions;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException(String msg) {
        super(msg);
    }
}