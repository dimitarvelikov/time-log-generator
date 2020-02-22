package com.outcons.interviewtask.services;

import com.outcons.interviewtask.persistence.dao.DbInitializationDAO;
import org.springframework.stereotype.Service;

@Service
public class DbInitializationService
{
    private final DbInitializationDAO dbInitializationDAO;


    public DbInitializationService(final DbInitializationDAO dbInitializationDAO)
    {
        this.dbInitializationDAO = dbInitializationDAO;
    }


    public void runDbInitProcedure()
    {
        dbInitializationDAO.runDbInitProcedure();
    }
}
