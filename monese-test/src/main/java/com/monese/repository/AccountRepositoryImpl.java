package com.monese.repository;

import com.monese.dto.responses.Account;
import com.monese.entity.AccountEntity;
import com.monese.exception.InsufficientBalanceException;
import com.monese.exception.InvalidAccountIdException;
import com.monese.exception.OptimisticLockingException;
import com.monese.repository.mappers.AccountEntityMapper;
import org.skife.jdbi.v2.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.time.Instant.now;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private Db db;

    @Override
    public void saveNewAccount(Account account) {
        db.execute(handle -> {
            handle.execute("insert into account values (?, ?, ?, ?)",
                account.accountId, account.accountType, account.balance, 0L);
        });
    }

    @Override
    public void depositToAccount(UUID accountId, long amount) {
        final AccountEntity ac = getAccount(accountId);
        db.execute(handle -> adjustAccountBalance(handle, accountId, ac.balance + abs(amount), abs(amount), "DEPOSIT", ac.version));
    }

    @Override
    public void withdrawFromAccount(UUID accountId, long amount) {
        final AccountEntity ac = getAccount(accountId);

        if (ac.balance - abs(amount) >= 0) {
            db.execute(handle -> adjustAccountBalance(handle, accountId, ac.balance - abs(amount), -abs(amount), "WITHDRAWAL", ac.version));
        } else {
            throw InsufficientBalanceException.INSTANCE;
        }
    }

    @Override
    public void transferAmount(UUID fromAccount, UUID toAccount, long amount) {
        db.execute(handle -> {

            final AccountEntity from = getAccount(fromAccount);
            final AccountEntity to = getAccount(toAccount);

            if (from.balance - abs(amount) >= 0) {
                adjustAccountBalance(handle, from.id,
                    from.balance - abs(amount),
                    -abs(amount),
                    format("Transfer to:%s", toAccount.toString()),
                    from.version);
            } else {
                throw InsufficientBalanceException.INSTANCE;
            }

            adjustAccountBalance(handle,
                to.id,
                to.balance + abs(amount),
                abs(amount),
                format("Transfer from:%s", fromAccount.toString()),
                to.version);
        });
    }

    private void adjustAccountBalance(Handle handle,
                                      UUID accountId,
                                      long newBalance,
                                      long amount,
                                      String description,
                                      long version) {

        handle.execute("insert into transactions values (?, ?, ?, ?, ?)",
            randomUUID(), accountId, amount, description, now());

        int rowsUpdated = handle.update("update account set balance = ?, version = ? where id = ? and version = ?",
            newBalance, version + 1, accountId, version);

        if (rowsUpdated != 1) {
            throw OptimisticLockingException.INSTANCE;
        }
    }

    @Override
    public Optional<AccountEntity> findAccountById(UUID accountId) {
        return db.executeAndGet(handle ->
                                    ofNullable(handle.createQuery("select id, accountType, balance, version from account where id = :id")
                                                   .bind("id", accountId)
                                                   .map(AccountEntityMapper.INSTANCE)
                                                   .first()));
    }

    private AccountEntity getAccount(UUID accountId) {
        return findAccountById(accountId)
                   .orElseThrow(() -> InvalidAccountIdException.INSTANCE);
    }
}
