package com.niels.homebanking.enumerate;

public enum Gender {

    M("M"),
    F("F"),
    NA("NA");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender fromValue(String value) {
        return switch (value) {
            case "M" -> Gender.M;
            case "F" -> Gender.F;
            case "NA" -> Gender.NA;
            default -> throw new IllegalArgumentException("Value [" + value
                    + "] not supported.");
        };
    }

}
