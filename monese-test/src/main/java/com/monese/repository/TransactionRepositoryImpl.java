package com.monese.repository;

import com.monese.entity.TransactionEntity;
import com.monese.repository.mappers.TransactionEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private Db db;

    @Override
    public List<TransactionEntity> getAllAccountTransactions(UUID accountId) {
        return db.executeAndGet(handle ->
                                    handle.createQuery("select id, accountId, description, amount, createdAt from transactions where accountId = :accountId order by createdAt desc")
                                        .bind("accountId", accountId)
                                        .map(TransactionEntityMapper.INSTANCE)
                                        .list());
    }
}
