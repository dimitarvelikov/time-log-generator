package com.outcons.interviewtask.models.bar_chart;

public class BarChartUserDTO
{
    private int user_id;

    private String full_name;

    private float time;

    private boolean compared;


    public int getUser_id()
    {
        return user_id;
    }


    public void setUser_id(final int user_id)
    {
        this.user_id = user_id;
    }


    public String getFull_name()
    {
        return full_name;
    }


    public void setFull_name(final String full_name)
    {
        this.full_name = full_name;
    }


    public float getTime()
    {
        return time;
    }


    public void setTime(final float time)
    {
        this.time = time;
    }


    public boolean isCompared()
    {
        return compared;
    }


    public void setCompared(final boolean compared)
    {
        this.compared = compared;
    }

}
