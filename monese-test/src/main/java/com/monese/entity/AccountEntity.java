package com.monese.entity;

import java.util.UUID;

import static com.monese.entity.AccountEntity.AccountType.SAVINGS;

public class AccountEntity {

    public enum AccountType {
        SAVINGS,
        INVESTMENT
    }

    public UUID id;
    public AccountType accountType;
    public long balance;
    public long version;

    public static class AccountEntityBuilder {
        public static AccountEntity newSavingsAccount() {
            return new AccountEntityBuilder()
                       .withId(UUID.randomUUID())
                       .withBalance(0L)
                       .withVersion(0L)
                       .withAccountType(SAVINGS)
                       .build();
        }

        public AccountEntityBuilder() {
        }

        public AccountEntity build() {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.balance = this.balance;
            accountEntity.id = this.id;
            accountEntity.accountType = this.accountType;
            accountEntity.version = this.version;
            return accountEntity;
        }

        private UUID id;
        private AccountType accountType;
        private long balance;
        private long version;

        public AccountEntityBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public AccountEntityBuilder withAccountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public AccountEntityBuilder withBalance(long balance) {
            this.balance = balance;
            return this;
        }

        public AccountEntityBuilder withVersion(long version) {
            this.version = version;
            return this;
        }
    }
}
