package com.outcons.interviewtask.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;


    public int getId()
    {
        return id;
    }


    public void setId(final int id)
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName(final String name)
    {
        this.name = name;
    }


    public String getSurname()
    {
        return surname;
    }


    public void setSurname(final String surname)
    {
        this.surname = surname;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(final String email)
    {
        this.email = email;
    }


    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
