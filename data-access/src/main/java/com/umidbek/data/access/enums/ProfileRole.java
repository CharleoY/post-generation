package com.umidbek.data.access.enums;

public enum ProfileRole {
    ADMIN,
    USER;

    public String authority() {
        return this.name();
    }
}