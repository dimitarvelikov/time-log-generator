package com.outcons.interviewtask.models.bar_chart;

public class BarChartProjectDTO
{
    private int user_id;

    private int project_id;

    private String project_name;

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


    public int getProject_id()
    {
        return project_id;
    }


    public void setProject_id(final int project_id)
    {
        this.project_id = project_id;
    }


    public String getProject_name()
    {
        return project_name;
    }


    public void setProject_name(final String project_name)
    {
        this.project_name = project_name;
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
