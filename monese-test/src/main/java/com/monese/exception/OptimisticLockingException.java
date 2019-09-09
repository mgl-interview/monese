package com.monese.exception;

public class OptimisticLockingException extends RuntimeException {
    public static final OptimisticLockingException INSTANCE = new OptimisticLockingException();
}
