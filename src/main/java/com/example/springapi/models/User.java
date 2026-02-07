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

    private String email;
    private String username;
    private String PassHash;

    public User(){}
    public User(String email, String PassHash, String username)
    {
        this.email = email;
        this.PassHash = PassHash;
        this.username = username;
    }


    public static User register(String email, String PassHash, String username)
    {
        return new User(email, PassHash, username);
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
