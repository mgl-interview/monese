package com.monese;

import com.monese.dto.responses.Account;
import com.monese.entity.AccountEntity;
import com.monese.exception.InsufficientBalanceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static com.monese.entity.AccountEntity.AccountType.SAVINGS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryImplTest extends Base {

    @Test
    public void account_should_be_persisted_properly() {
        // When
        Account newSavingsAccount = accountService.createNewSavingsAccount();
        Optional<AccountEntity> accountDetails = accountService.getAccountDetails(newSavingsAccount.accountId);

        // Then
        assertTrue("Account creation should be successful", accountDetails.isPresent());
        assertEquals("Account type should be correct", SAVINGS, accountDetails.get().accountType);
        assertEquals("Account balance should be zero", 0L, accountDetails.get().balance);
    }

    @Test
    public void deposit_to_account_should_be_done_correctly() {
        // Given
        UUID newAccount = createNewAccount();

        // When
        depositToAccount(newAccount, 5L);

        // Then
        AccountEntity accountDetails = accountService.getAccountDetails(newAccount).get();

        assertEquals("Account balance should be correct", 5L, accountDetails.balance);
    }

    @Test
    public void withdraw_to_account_should_be_done_correctly() {
        // Given
        UUID newAccount = createNewAccount();

        // When
        depositToAccount(newAccount, 5L);
        withdrawFromAccount(newAccount, 2L);

        // Then
        AccountEntity accountDetails = accountService.getAccountDetails(newAccount).get();

        assertEquals("Account balance should be correct", 3L, accountDetails.balance);
    }

    @Test(expected = InsufficientBalanceException.class)
    public void withdraw_more_money_than_the_user_has_should_not_be_allowed() {
        // Given
        UUID newAccount = createNewAccount();

        // When
        depositToAccount(newAccount, 5L);
        withdrawFromAccount(newAccount, 7L);

        // Then
        AccountEntity accountDetails = accountService.getAccountDetails(newAccount).get();
    }

    @Test
    public void when_user_has_enough_balance_transfer_should_be_done_correctly() {
        // Given
        UUID from = createNewAccount();
        UUID to = createNewAccount();

        // When
        depositToAccount(from, 5L);
        transfer(from, to, 2L);

        // Then
        assertEquals("Sender should have correct balance", 3L, obtainBalance(from));
        assertEquals("Recipient should have correct balance", 2L, obtainBalance(to));
    }

    @Rule
    public ExpectedException RULE = ExpectedException.none();

    @Test
    public void when_user_has_insufficient_balance_no_money_movement_should_happen_at_all() {
        // Given
        UUID from = createNewAccount();
        UUID to = createNewAccount();

        // When
        depositToAccount(from, 100L);
        depositToAccount(to, 200L);
        Optional<Throwable> ex = suppress(() -> transfer(from, to, 101L));

        // Then
        assertTrue("Exception was thrown", ex.isPresent());
        assertEquals("Exception was InsufficientBalanceException", InsufficientBalanceException.INSTANCE, ex.get());

        assertEquals("Sender should have correct balance", 100L, obtainBalance(from));
        assertEquals("Recipient should have correct balance", 200L, obtainBalance(to));
    }
}

