package com.monese.repository;

import com.monese.exception.OptimisticLockingException;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class Db {

    private static final int MAX_RETRIES = 3;

    @Autowired
    DBI dbi;

    public <T> T executeAndGet(Function<Handle, T> queryCallback) {
        try (final Handle handle = dbi.open()) {
            return queryCallback.apply(handle);
        }
    }

    public void execute(Consumer<Handle> queryCallback) {
        int curTry = 1;
        boolean successFlag = false;

        while (curTry < MAX_RETRIES && !successFlag) {
            try (final Handle handle = dbi.open()) {
                handle.begin();
                try {
                    queryCallback.accept(handle);
                    handle.commit();
                    successFlag = true;
                } catch (OptimisticLockingException ex) {
                    handle.rollback();
                    successFlag = false;
                    curTry++;
                }
            }
        }
    }
}
