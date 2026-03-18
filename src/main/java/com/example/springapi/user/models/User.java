package com.example.springapi.user.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){}
    public User(String email, String passHash, String username)
    {
        this.email = email;
        this.passHash = passHash;
        this.username = username;
    }

}
