CREATE TABLE ACCOUNT (
	id UUID primary key,
	accountType varchar(50) not null,
	balance int not null,
	version int not null
);

CREATE TABLE TRANSACTIONS(
	id UUID primary key,
	accountId UUID not null,
	amount int not null,
	description varchar(100) not null,
	createdAt timestamp not null,
	foreign key (accountId) references ACCOUNT(id)
);

CREATE INDEX TRANSACTIONS_ACCOUNT_ID ON TRANSACTIONS(accountId);