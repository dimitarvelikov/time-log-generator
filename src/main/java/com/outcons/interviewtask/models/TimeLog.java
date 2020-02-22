package com.outcons.interviewtask.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class TimeLog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column
    private LocalDate date;

    @Column
    private float time;


    public int getId()
    {
        return id;
    }


    public void setId(final int id)
    {
        this.id = id;
    }


    public User getUser()
    {
        return user;
    }


    public void setUser(final User user)
    {
        this.user = user;
    }


    public Project getProject()
    {
        return project;
    }


    public void setProject(final Project project)
    {
        this.project = project;
    }


    public LocalDate getDate()
    {
        return date;
    }


    public void setDate(final LocalDate date)
    {
        this.date = date;
    }


    public float getTime()
    {
        return time;
    }


    public void setTime(final float time)
    {
        this.time = time;
    }
}
