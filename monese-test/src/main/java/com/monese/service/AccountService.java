package com.monese.service;

import com.monese.dto.requests.BalanceTransactionRequest;
import com.monese.dto.responses.Account;
import com.monese.entity.AccountEntity;
import com.monese.entity.TransactionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Account createNewSavingsAccount();

    Optional<AccountEntity> getAccountDetails(UUID accountId);

    void updateAccountBalance(UUID accountId, BalanceTransactionRequest req);

    void accountTransfer(UUID accountIdFrom, UUID accountIdTo, long amountToTransfer);

    List<TransactionEntity> getAccountStatement(UUID accountId);
}
