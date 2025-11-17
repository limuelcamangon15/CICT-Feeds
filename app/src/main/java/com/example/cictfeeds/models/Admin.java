package com.example.cictfeeds.models;

public class Admin {
    private String email = "cictadmin@gmail.com";
    private String password = "CICTadmin@123";

    private String firstName = "CICT";
    private String lastName = "Admin";
    private String specialization;
    private String yearLevel;
    private String course;
    private String section;



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

    public void setYearLevel(String yearLevel){ this.yearLevel = yearLevel; }
    public String getYearLevel(){ return this.yearLevel; }

    public void setCourse(String course){ this.course = course; }
    public String getCourse(){ return this.course; }

    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getSpecialization() { return this.specialization; }
    public void setSection(String section) { this.section = section; }
    public String getSection() { return this.section; }


}
