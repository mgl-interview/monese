### Project Description

The project has been created using **SpringBoot** with **JDBI** for database access. The database used is an in memory version of **H2**. 

**Flyway** migration tool is used for creating database schema which can be found in the resources folder (db/migration)

Since its a **Gradle** project it can be build using `gradle build` and then it can be run with `java -jar \build\libs\demo-1.0.jar` 

H2 console is activated and can be accessed to offer a view of the database. (http://localhost:8080/h2-console/ Then choose `jdbc : h2 : mem : testdb` as JDBC URL and username `sa` & empty password.


##### GET (localhost:8080/accounts/new)
> Creates a new savings account and returns balance info and account id (UUID)

<pre>
{
    "account_id": "f91ee40e-9f28-49d0-9d3c-78d85c03335a",
    "account_type": "SAVINGS",
    "balance": 0
}
</pre>

##### GET (localhost:8080/accounts/{account_id}/statement)
> Returns the list of all the transactions of the requested account in reverse order (most recent first)

- Response

<pre>
[
    {
        "description": "WITHDRAWAL",
        "amount": -10,
        "txTime": "2019-09-09T10:31:57.000456103Z"
    },
    {
        "description": "DEPOSIT",
        "amount": 100,
        "txTime": "2019-09-09T10:31:51.000381756Z"
    },
    {
        "description": "DEPOSIT",
        "amount": 100,
        "txTime": "2019-09-09T10:31:49.000802665Z"
    }
]
</pre>

##### GET (localhost:8080/accounts/{account_id}/statement)
> Returns the list of all the transactions of the requested account in reverse order (most recent first)

- Response

<pre>
[
    {
        "description": "WITHDRAWAL",
        "amount": -10,
        "txTime": "2019-09-09T10:31:57.000456103Z"
    },
    {
        "description": "DEPOSIT",
        "amount": 100,
        "txTime": "2019-09-09T10:31:51.000381756Z"
    },
    {
        "description": "DEPOSIT",
        "amount": 100,
        "txTime": "2019-09-09T10:31:49.000802665Z"
    }
]
</pre>


##### PUT (localhost:8080/accounts/{account_id}/balance)
> Creates a new transaction (either a deposit or withdrawal) and adjusts the balance accordingly.

- Request

<pre>
{
    "type": "DEPOSIT",
    "amount": 100
}
</pre>

- Response
The response is HTTP status code 200 OK with no further info.


##### GET (localhost:8080/accounts/{account_id})
> Retrieves account information (balance, type, id)

- Response

<pre>
{
    "account_id": "f91ee40e-9f28-49d0-9d3c-78d85c03335a",
    "account_type": "SAVINGS",
    "balance": 290
}
</pre>

##### POST (localhost:8080/accounts/money-transfer)
> Transfers the amount of requested from one acccount to another.

- Request

<pre>
{
    "toAccountId": "7b6f3083-f679-4237-89e6-df718f44be0f",
    "fromAccountId": "85e7e778-f292-4f4d-9269-0e167a9b85d6",
    "amount": 10
}
</pre>

- Response
The response is HTTP status code 200 OK with no further info.




