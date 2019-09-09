package com.monese.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.monese.dto.requests.BalanceTransactionRequest.TransactionType.DEPOSIT;
import static com.monese.dto.requests.BalanceTransactionRequest.TransactionType.WITHDRAWAL;

public class BalanceTransactionRequest {

    public static BalanceTransactionRequest deposit(long amount) {
        return new BalanceTransactionRequest(DEPOSIT, amount);
    }

    public static BalanceTransactionRequest withdrawal(long amount) {
        return new BalanceTransactionRequest(WITHDRAWAL, amount);
    }

    @JsonCreator
    public BalanceTransactionRequest(
        @JsonProperty("type") TransactionType type,
        @JsonProperty("amount") long amount) {

        this.type = type;
        this.amount = amount;
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }

    public final TransactionType type;
    public final long amount;
}
