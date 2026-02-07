package com.example.springapi.models;


import jakarta.persistence.*;


@Entity
@Table(name = "spring_users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;

    public User(){}
    public User(String username)
    {
        this.username = username;

    }


    public static User createUser(String username, Long id)
    {
        return new User(username);
    }

    public String GetUsername()
    {
        return username;
    }
    public Long GetId()
    {
        return id;
    }

    public void SetUsername(String username)
    {
        this.username = username;
    }
    public void SetId(Long id)
    {
        this.id = id;
    }
}
