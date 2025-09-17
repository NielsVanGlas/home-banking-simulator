package com.niels.homebanking.enumerate;

public enum DocumentType {

    PASSPORT("PASSPORT"),
    ID("ID CARD");

    private final String value;

    private DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DocumentType fromValue(String value) {
        return switch (value) {
            case "PASSPORT" -> DocumentType.PASSPORT;
            case "ID CARD" -> DocumentType.ID;
            default -> throw new IllegalArgumentException("Value [" + value
                    + "] not supported.");
        };
    }

}
