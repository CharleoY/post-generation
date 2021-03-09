package com.umidbek.webapi.exception;

public class UserNotFoundException extends RuntimeException {

    private final String message;

    public UserNotFoundException(String s) {
        super(s);
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
