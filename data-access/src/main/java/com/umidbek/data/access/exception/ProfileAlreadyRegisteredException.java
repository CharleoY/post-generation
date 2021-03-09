package com.umidbek.data.access.exception;

public class ProfileAlreadyRegisteredException extends RuntimeException {
    private final String message;

    public ProfileAlreadyRegisteredException(String s) {
        super(s);
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
