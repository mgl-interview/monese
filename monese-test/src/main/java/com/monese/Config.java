package com.monese;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Scope("singleton")
    public DataSource datasource() {
        return JdbcConnectionPool.create(dbUrl, username, password);
    }

    @Bean
    @Scope("singleton")
    public DBI dbi() {
        return new DBI(datasource());
    }
}
