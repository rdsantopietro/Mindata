package com.mindata.superHero.error;

public class IdNotNullException extends RuntimeException {

    public IdNotNullException() {
        super("ID must be null for this operation.");
    }

    public IdNotNullException(String message) {
        super(message);
    }
}