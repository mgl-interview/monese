package com.monese;

import com.monese.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static com.monese.dto.requests.BalanceTransactionRequest.deposit;
import static com.monese.dto.requests.BalanceTransactionRequest.withdrawal;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public abstract class Base {

    public static String UUID_REGEX = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b";

    @Autowired
    protected AccountService accountService;

    protected UUID uuid() {
        return UUID.randomUUID();
    }

    protected UUID createNewAccountWithBalance(long amount) {
        UUID uuid = accountService.createNewSavingsAccount().accountId;
        depositToAccount(uuid, amount);
        return uuid;
    }

    long obtainBalance(UUID uuid) {
        return accountService.getAccountDetails(uuid)
                   .map(a -> a.balance)
                   .orElseThrow(()-> new RuntimeException("Accound not fould"));
    }

    protected UUID createNewAccount() {
        return accountService.createNewSavingsAccount().accountId;
    }

    protected void transfer(UUID from, UUID to, long amount) {
        accountService.accountTransfer(from, to, amount);
    }

    protected void depositToAccount(UUID accountId, long amount) {
        accountService.updateAccountBalance(accountId, deposit(amount));
    }

    protected void withdrawFromAccount(UUID accountId, long amount) {
        accountService.updateAccountBalance(accountId, withdrawal(amount));
    }

    protected <T> Optional<Throwable> suppress(Runnable r) {
        try {
            r.run();
            return empty();
        } catch (Throwable ex) {
            return of(ex);
        }
    }
}
