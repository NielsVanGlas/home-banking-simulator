package com.niels.homebanking.enumerate;

public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromValue(String value) {
        return switch (value) {
            case "ADMIN" -> Role.ADMIN;
            case "USER" -> Role.USER;
            default -> throw new IllegalArgumentException("Value [" + value
                    + "] not supported.");
        };
    }

}
