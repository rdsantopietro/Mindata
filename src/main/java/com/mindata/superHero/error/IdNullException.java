package com.mindata.superHero.error;

public class IdNullException extends RuntimeException {

    public IdNullException() {
        super("ID must be null for this operation.");
    }

    public IdNullException(String message) {
        super(message);
    }
}