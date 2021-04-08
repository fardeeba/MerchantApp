package com.example.merchantapp;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("fullName")
    String fullName;

    @SerializedName("phoneNumber")
    String phoneNumber;

    @SerializedName("email")
    String email;

    public String getFullName()
    {
        return fullName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }
}
