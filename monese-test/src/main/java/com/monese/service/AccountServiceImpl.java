package com.monese.service;

import com.monese.dto.requests.BalanceTransactionRequest;
import com.monese.dto.responses.Account;
import com.monese.entity.AccountEntity;
import com.monese.entity.TransactionEntity;
import com.monese.repository.AccountRepository;
import com.monese.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.monese.dto.requests.BalanceTransactionRequest.TransactionType.DEPOSIT;
import static com.monese.dto.requests.BalanceTransactionRequest.TransactionType.WITHDRAWAL;
import static com.monese.entity.AccountEntity.AccountType.SAVINGS;
import static java.util.UUID.randomUUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Account createNewSavingsAccount() {

        Account ac = new Account(randomUUID(), SAVINGS, 0L);
        accountRepository.saveNewAccount(ac);
        return ac;
    }

    @Override
    public Optional<AccountEntity> getAccountDetails(UUID accountId) {
        return accountRepository.findAccountById(accountId);
    }

    @Override
    public void updateAccountBalance(UUID accountId, BalanceTransactionRequest request) {

        if (request.type == DEPOSIT) {
            accountRepository.depositToAccount(accountId, request.amount);
        } else if (request.type == WITHDRAWAL) {
            accountRepository.withdrawFromAccount(accountId, request.amount);
        } else {
            throw new IllegalStateException("Unknown transaction type.");
        }
    }

    @Override
    public void accountTransfer(UUID accountIdFrom, UUID accountIdTo, long amountToTransfer) {
        if (amountToTransfer <= 0) {
            throw new IllegalArgumentException("Amount must be strictly positive");
        }

        accountRepository.transferAmount(accountIdFrom, accountIdTo, amountToTransfer);
    }

    @Override
    public List<TransactionEntity> getAccountStatement(UUID accountId) {
        return transactionRepository.getAllAccountTransactions(accountId);
    }
}
