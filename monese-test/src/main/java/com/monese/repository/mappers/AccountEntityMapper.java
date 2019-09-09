package com.monese.repository.mappers;

import com.monese.entity.AccountEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.UUID.fromString;

public final class AccountEntityMapper implements ResultSetMapper<AccountEntity> {

    public static final AccountEntityMapper INSTANCE = new AccountEntityMapper();

    private AccountEntityMapper() {
    }

    @Override
    public AccountEntity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new AccountEntity.AccountEntityBuilder()
                   .withId(fromString(r.getString("id")))
                   .withAccountType(AccountEntity.AccountType.valueOf(r.getString("accountType")))
                   .withBalance(r.getInt("balance"))
                   .withVersion(r.getLong("version"))
                   .build();
    }
}
