package com.monese.exception;

public final class InsufficientBalanceException extends IllegalStateException {

    public static final InsufficientBalanceException INSTANCE = new InsufficientBalanceException();

    private InsufficientBalanceException() {
        super("Account id does not have enough balance.");
    }
}
