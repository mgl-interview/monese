package com.monese;

import com.monese.entity.TransactionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionRepositoryImplTest extends Base {

    @Test
    public void account_transactions_should_be_stored_properly() {
        // Given
        UUID newAccount = createNewAccount();

        // When
        depositToAccount(newAccount, 100L);
        withdrawFromAccount(newAccount, 10L);
        depositToAccount(newAccount, 20L);

        // Then
        List<TransactionEntity> accountDetails = accountService.getAccountStatement(newAccount);

        assertEquals("There should be 3 transactions exactly", 3L, accountDetails.size());

        assertEquals("Transactions should be reported in a most recent order.", 20L, accountDetails.get(0).amount);
        assertEquals("Transactions should be reported in a most recent order.", -10L, accountDetails.get(1).amount);
        assertEquals("Transactions should be reported in a most recent order.", 100L, accountDetails.get(2).amount);

        assertEquals("DEPOSIT", accountDetails.get(0).description);
        assertEquals("WITHDRAWAL", accountDetails.get(1).description);
        assertEquals("DEPOSIT", accountDetails.get(2).description);
    }
}
