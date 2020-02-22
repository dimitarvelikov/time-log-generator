package com.outcons.interviewtask.services;

import java.util.List;

import com.outcons.interviewtask.models.User;
import com.outcons.interviewtask.models.data_table.DataTableResp;
import com.outcons.interviewtask.persistence.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserDAO userDAO;


    public UserService(final UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }


    public List<User> getAllUsers()
    {
        return userDAO.getAllUsers();
    }


    public DataTableResp getUsersPaginated(final int sEcho,
                                           final int firstRowId,
                                           final int rowsPerPage,
                                           final int sortColNum)
    {
        final int userRecords = userDAO.getUserRecordsCount();

        return new DataTableResp(
                sEcho,
                userRecords,
                userRecords,
                userDAO.getUsersPaginated(
                        firstRowId,
                        rowsPerPage,
                        sortColNum));
    }


    public List<User> getUsersPaginated(final int firstRowId, final int rowsPerPage, final int sortColNum)
    {
        return userDAO.getUsersPaginated(firstRowId, rowsPerPage, sortColNum);
    }
}
