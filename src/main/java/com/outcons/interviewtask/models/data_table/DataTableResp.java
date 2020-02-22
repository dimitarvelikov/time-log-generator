package com.outcons.interviewtask.models.data_table;

import java.util.List;

import com.outcons.interviewtask.models.User;

public class DataTableResp
{
    private int sEcho;

    private int iTotalRecords;

    private int iTotalDisplayRecords;

    private List<User> aaData;


    public DataTableResp(final int sEcho,
                         final int iTotalRecords,
                         final int iTotalDisplayRecords,
                         final List<User> aaData)
    {
        this.sEcho = sEcho;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.aaData = aaData;
    }


    public int getsEcho()
    {
        return sEcho;
    }


    public void setsEcho(final int sEcho)
    {
        this.sEcho = sEcho;
    }


    public int getiTotalRecords()
    {
        return iTotalRecords;
    }


    public void setiTotalRecords(final int iTotalRecords)
    {
        this.iTotalRecords = iTotalRecords;
    }


    public int getiTotalDisplayRecords()
    {
        return iTotalDisplayRecords;
    }


    public void setiTotalDisplayRecords(final int iTotalDisplayRecords)
    {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }


    public List<User> getAaData()
    {
        return aaData;
    }


    public void setAaData(final List<User> aaData)
    {
        this.aaData = aaData;
    }
}
