package com.example.springapi.user.models;

public enum Role
{
    USER,
    ADMIN;

    public static Role fromString(String value)
    {
        try
        {
            return Role.valueOf(value.toUpperCase());

        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Invalid role : " + value);
        }

    }
}
