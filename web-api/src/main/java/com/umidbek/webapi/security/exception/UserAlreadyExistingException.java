package com.umidbek.webapi.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by dilsh0d on 11.02.2017.
 */
public class UserAlreadyExistingException extends AuthenticationException {
    public UserAlreadyExistingException(String msg) {
        super(msg);
    }
}
