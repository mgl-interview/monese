package com.monese.converters;

import com.monese.dto.responses.Account;
import com.monese.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<AccountEntity, Account> {

    @Override
    public AccountEntity toEntity(Account account) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Account toDto(AccountEntity entity) {
        return new Account(entity.id, entity.accountType, entity.balance);
    }
}
