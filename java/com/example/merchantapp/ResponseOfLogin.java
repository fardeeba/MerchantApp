package com.example.merchantapp;

public class ResponseOfLogin {

    private String token;

    public String getToken()
    {
        return token;
    }

    User user = new User();

    public String getUserNameInfo()
    {
        return user.getFullName();
    }
    public String getUserContactInfo()
    {
        return user.getPhoneNumber();
    }
    public String getUserMailInfo()
    {
        return user.getEmail();
    }
}
