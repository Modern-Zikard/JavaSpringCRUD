package com.example.springapi.user.models;


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
    private String passHash;

    public User(){}
    public User(String email, String passHash, String username)
    {
        this.email = email;
        this.passHash = passHash;
        this.username = username;
    }

    public String getEmail(){return this.email;}
    public String getPassHash() { return this.passHash;}
    public String getUsername() { return this.username;}
    public Long getId() {return this.id;}

    public void setUsername(String username) {this.username = username;}
    public void setId(Long id) {this.id = id;}
}
