package com.monese.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

public class TransactionEntity {

    public UUID id;
    public UUID accountId;
    public Instant createdAt;
    public String description;
    public long amount;

    public TransactionEntity(UUID id, UUID accountId, Instant createdAt, String description, long amount) {
        this.id = id;
        this.accountId = accountId;
        this.createdAt = createdAt;
        this.description = description;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return amount == that.amount &&
                   Objects.equals(id, that.id) &&
                   Objects.equals(accountId, that.accountId) &&
                   Objects.equals(createdAt, that.createdAt) &&
                   Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, createdAt, description, amount);
    }


    public static class TransactionEntityBuilder {
        public static TransactionEntity newTransaction(UUID accountId, String description, long amount) {
            return new TransactionEntityBuilder()
                       .withId(randomUUID())
                       .withAccountId(accountId)
                       .withCreatedAt(now())
                       .withAmount(amount)
                       .withDescription(description)
                       .build();
        }

        public TransactionEntityBuilder() {
        }

        public TransactionEntity build() {
            return new TransactionEntity(id, accountId, createdAt, description, amount);
        }

        private UUID id;
        private UUID accountId;
        private String description;
        private long amount;
        private Instant createdAt;


        public TransactionEntityBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public TransactionEntityBuilder withAccountId(UUID id) {
            this.accountId = id;
            return this;
        }

        public TransactionEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TransactionEntityBuilder withAmount(long amount) {
            this.amount = amount;
            return this;
        }

        public TransactionEntityBuilder withCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }
    }
}
