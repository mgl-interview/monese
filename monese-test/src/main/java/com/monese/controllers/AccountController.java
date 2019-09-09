package com.monese.controllers;

import com.monese.converters.AccountConverter;
import com.monese.converters.TransactionConverter;
import com.monese.dto.requests.BalanceTransactionRequest;
import com.monese.dto.requests.MoneyTransferRequest;
import com.monese.dto.responses.Account;
import com.monese.dto.responses.Transaction;
import com.monese.exception.InvalidAccountIdException;
import com.monese.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private TransactionConverter transactionConverter;

    @GetMapping(value = "/new")
    public Account getAccountDetails() {
        return accountService.createNewSavingsAccount();
    }

    @GetMapping(value = "/{accountId}/statement")
    public List<Transaction> getAccountStatement(@PathVariable UUID accountId) {
        return accountService.getAccountStatement(accountId)
                   .stream()
                   .map(e -> transactionConverter.toDto(e))
                   .collect(Collectors.toList());
    }

    @GetMapping(value = "/{accountId}")
    public Account getAccountDetails(@PathVariable UUID accountId) {
        return accountService.getAccountDetails(accountId)
                   .map(entity -> accountConverter.toDto(entity))
                   .orElseThrow(() -> InvalidAccountIdException.INSTANCE);
    }

    @PutMapping(value = "/{accountId}/balance")
    public void adjustBalance(@PathVariable UUID accountId, @RequestBody BalanceTransactionRequest request) {
        accountService.updateAccountBalance(accountId, request);
    }

    @PostMapping(value = "/money-transfer")
    public void adjustBalance(@RequestBody MoneyTransferRequest request) {
        accountService.accountTransfer(request.fromAccountId, request.toAccountId, request.amount);
    }
}

