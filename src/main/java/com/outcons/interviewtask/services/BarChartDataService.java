package com.outcons.interviewtask.services;

import java.util.List;
import java.util.Optional;

import com.outcons.interviewtask.models.bar_chart.BarChartProjectDTO;
import com.outcons.interviewtask.models.bar_chart.BarChartUserDTO;
import com.outcons.interviewtask.persistence.dao.BarChartDAO;
import org.springframework.stereotype.Service;

@Service
public class BarChartDataService
{
    private final BarChartDAO barChartDAO;


    public BarChartDataService(final BarChartDAO barChartDAO)
    {
        this.barChartDAO = barChartDAO;
    }


    public List<BarChartUserDTO> getTopTenUsers(final Optional<String> from, final Optional<String> to)
    {
        return barChartDAO.getTopTenUsers(from, to);
    }


    public List<BarChartUserDTO> getTopTenUsersAndUserToCompare(final int userId,
                                                                final Optional<String> from,
                                                                final Optional<String> to)
    {
        boolean isUserIdTopTen = false;
        final List<BarChartUserDTO> users = barChartDAO.getTopTenUsers(from, to);
        for (BarChartUserDTO u : users)
        {
            if (u.getUser_id() == userId)
            {
                u.setCompared(true);
                isUserIdTopTen = true;
            }
        }

        if (!isUserIdTopTen)
        {
            users.add(barChartDAO.getSingleUserTime(userId, from, to));
        }

        return users;
    }


    public List<BarChartProjectDTO> getTopTenUsersForProject(final Optional<String> from,
                                                             final Optional<String> to)
    {
        return barChartDAO.getTopTenUsersForProject(from, to);
    }


    public List<BarChartProjectDTO> getTopTenUsersForProjectAndUserToCompare(final int userId,
                                                                             final Optional<String> from,
                                                                             final Optional<String> to)
    {
        boolean isUserIdTopTen = false;
        final List<BarChartProjectDTO> users = barChartDAO.getTopTenUsersForProject(from, to);
        for (BarChartProjectDTO u : users)
        {
            if (u.getUser_id() == userId)
            {
                u.setCompared(true);
                isUserIdTopTen = true;
            }
        }
        if (!isUserIdTopTen)
        {
            users.add(barChartDAO.getSingleUserForProject(userId, from, to));
        }


        return users;
    }

}
