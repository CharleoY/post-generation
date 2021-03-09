package com.umidbek.webapi.exception;

public class OpenAiException extends RuntimeException {
    private final String message;

    public OpenAiException(String s) {
        super(s);
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
