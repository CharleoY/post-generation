package com.umidbek.webapi.exception;

public class TweetNotFound extends RuntimeException {

    private final String message;

    public TweetNotFound(String s) {
        super(s);
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
