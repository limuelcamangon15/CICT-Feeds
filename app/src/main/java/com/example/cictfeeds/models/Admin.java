package com.example.cictfeeds.models;

public class Admin {
    private String email = "cictadmin@gmail.com";
    private String password = "CICTadmin@123";

    private String firstName = "CICT";
    private String lastName = "Admin";


    public Admin(){}

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }
}
