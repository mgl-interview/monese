package com.monese.dto.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.monese.entity.AccountEntity;

import java.util.UUID;

public class Account {

    @JsonCreator
    public Account(
        @JsonProperty("account_id") UUID accountId,
        @JsonProperty("account_type") AccountEntity.AccountType accountType,
        @JsonProperty("balance") long balance) {

        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public final UUID accountId;
    public final AccountEntity.AccountType accountType;
    public final long balance;
}
