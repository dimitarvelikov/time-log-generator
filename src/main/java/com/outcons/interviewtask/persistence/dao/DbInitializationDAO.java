package com.outcons.interviewtask.persistence.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbInitializationDAO
{
    private final JdbcTemplate jdbc;

    @Value("${db.generator.users.amount}")
    private int numberUsers;


    public DbInitializationDAO(final JdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }


    /**
     * Trucate tables user,project,time-log
     * Populates the tables with new records
     */
    public void runDbInitProcedure()
    {
        jdbc.update("CALL initializeDb(?)", numberUsers);
    }

}
