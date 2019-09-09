package com.monese.repository;

import com.monese.dto.responses.Account;
import com.monese.entity.AccountEntity;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    void saveNewAccount(Account account);
    void depositToAccount(UUID accountId, long amount);
    void withdrawFromAccount(UUID accountId, long amount);
    void transferAmount(UUID fromAccount, UUID toAccount, long amount);

    Optional<AccountEntity> findAccountById(UUID accountId);
}
