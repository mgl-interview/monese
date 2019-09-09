package com.monese.exception;

public final class InvalidAccountIdException extends IllegalArgumentException {

    public static final InvalidAccountIdException INSTANCE = new InvalidAccountIdException();

    private InvalidAccountIdException() {
        super("Account id does not exist.");
    }
}
