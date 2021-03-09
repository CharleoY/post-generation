package com.umidbek.data.access;

public class ProfileNotFoundException extends RuntimeException {

    private final String message;

    public ProfileNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
