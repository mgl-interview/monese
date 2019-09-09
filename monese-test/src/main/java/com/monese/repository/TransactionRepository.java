package com.monese.repository;

import com.monese.entity.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    List<TransactionEntity> getAllAccountTransactions(UUID fromAccount);
}