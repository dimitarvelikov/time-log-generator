package com.outcons.interviewtask.models.data_table;

public class DataTblRq
{
    private int sEcho;

    private int firstRowId;

    private int rowsPerPage;

    private int sortCol;


    public int getsEcho()
    {
        return sEcho;
    }


    public int getFirstRowId()
    {
        return firstRowId;
    }


    public int getRowsPerPage()
    {
        return rowsPerPage;
    }


    public int getSortCol()
    {
        return sortCol;
    }


    @Override
    public String toString()
    {
        return "DataTblRq{" +
                "sEcho=" + sEcho +
                ", firstRowId=" + firstRowId +
                ", rowsPerPage=" + rowsPerPage +
                ", sortCol=" + sortCol +
                '}';
    }
}
