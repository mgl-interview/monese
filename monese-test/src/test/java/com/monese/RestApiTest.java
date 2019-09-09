package com.monese;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestApiTest extends Base {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void account_creation_rest_response_should_be_correct() throws Exception {

        mockMvc.perform(get("/accounts/new"))
            .andExpect(jsonPath("$.account_id", matchesPattern(UUID_REGEX)))
            .andExpect(jsonPath("$.account_type", equalToIgnoringCase("SAVINGS")))
            .andExpect(jsonPath("$.balance", is(0)))
            .andExpect(status().isOk());
    }

    @Test
    public void account_balance_rest_response_should_be_correct() throws Exception {

        // Given
        UUID id = createNewAccountWithBalance(5);

        // Then
        mockMvc.perform(get(format("/accounts/%s", id.toString())))
            .andExpect(jsonPath("$.account_id", equalTo(id.toString())))
            .andExpect(jsonPath("$.account_type", equalToIgnoringCase("SAVINGS")))
            .andExpect(jsonPath("$.balance", is(5)))
            .andExpect(status().isOk());
    }

    @Test
    public void account_statement_rest_response_should_return_all_transactions_in_the_correct_order() throws Exception {

        // Given
        UUID id = createNewAccountWithBalance(7);
        withdrawFromAccount(id, 3);

        // Then
        mockMvc.perform(get(format("/accounts/%s/statement", id.toString())))
            .andExpect(jsonPath("$.[0].txTime", notNullValue()))
            .andExpect(jsonPath("$.[0].amount", equalTo(-3)))
            .andExpect(jsonPath("$.[0].description", equalToIgnoringCase("WITHDRAWAL")))
            .andExpect(jsonPath("$.[1].txTime", notNullValue()))
            .andExpect(jsonPath("$.[1].amount", equalTo(7)))
            .andExpect(jsonPath("$.[1].description", equalToIgnoringCase("DEPOSIT")))
            .andExpect(status().isOk());
    }

    @Test
    public void account_transfer_should_work_properly() throws Exception {

        // Given
        UUID from = createNewAccountWithBalance(200);
        UUID to = createNewAccountWithBalance(300);
        int amount = 50;

        // Then
        mockMvc.perform(
            post("/accounts/money-transfer")
                .content(format("{ \"toAccountId\": \"%s\", \"fromAccountId\": \"%s\", \"amount\":%d }", to, from, amount))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        // And
        assertEquals("Sender's account should be updated accordingly", 150, obtainBalance(from));
        assertEquals("Recipient's account should be updated accordingly", 350, obtainBalance(to));
    }
}
