package com.monese.repository.mappers;

import com.monese.entity.TransactionEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.util.UUID.fromString;

public class TransactionEntityMapper implements ResultSetMapper<TransactionEntity> {

    public static final TransactionEntityMapper INSTANCE = new TransactionEntityMapper();

    public DateTimeFormatter FORMATTER = DateTimeFormatter
                                             .ofPattern("yyyy-MM-dd HH:mm:ss.n")
                                             .withZone(ZoneId.of("Europe/London"));

    @Override
    public TransactionEntity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new TransactionEntity.TransactionEntityBuilder()
                   .withId(fromString(r.getString("id")))
                   .withAccountId(fromString(r.getString("accountId")))
                   .withCreatedAt(Instant.from(FORMATTER.parse(r.getString("createdAt"))))
                   .withDescription(r.getString("description"))
                   .withAmount(r.getLong("amount"))
                   .build();
    }
}
