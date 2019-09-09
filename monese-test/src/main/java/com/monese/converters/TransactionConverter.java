package com.monese.converters;

import com.monese.dto.responses.Transaction;
import com.monese.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter implements Converter<TransactionEntity, Transaction> {

    @Override
    public TransactionEntity toEntity(Transaction transaction) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Transaction toDto(TransactionEntity transactionEntity) {
        return new Transaction(transactionEntity.description, transactionEntity.amount,transactionEntity.createdAt);
    }
}
