package com.monese.dto.responses;

import java.time.Instant;

public final class Transaction {

    public Transaction(String description, long amount, Instant txTime) {
        this.description = description;
        this.amount = amount;
        this.txTime = txTime;
    }

    public final String description;
    public final long amount;
    public final Instant txTime;
}
