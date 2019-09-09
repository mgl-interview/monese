package com.monese.dto.requests;

import java.util.UUID;

public class MoneyTransferRequest {

    public UUID fromAccountId;
    public UUID toAccountId;
    public long amount;
}
