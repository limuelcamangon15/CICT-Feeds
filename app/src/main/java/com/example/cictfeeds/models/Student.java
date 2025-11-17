package com.example.cictfeeds.models;

import java.util.ArrayList;

public class Student {
    private String studentId;
    private String firstname;
    private String lastname;
    private String course;
    private int year;
    private String section;
    private String email;
    private String password;
    private String birthday;
    private String gender;
    private String specialization;
    private ArrayList<String> likedPostById;

    public Student(String studentId, String firstname, String lastname, String course, int year, String section,
                   String email, String password, String birthday, String gender, String specialization, ArrayList<String> likedPostById) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.course = course;
        this.year = year;
        this.section = section;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.specialization = specialization;
        this.likedPostById = likedPostById;
    }

    public Student(String studentId, String firstname, String lastname, String email, String password, String course, int year) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.course = course;
        this.year = year;
    }

    public Student(String firstname, String lastname, String course, int year,
                   String email, String password) {
        this(null, firstname, lastname, course, year, null, email, password, null, null, null, null);
    }

    //getters and setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void addLikedPost(String postId) {
        if (likedPostById == null) likedPostById = new ArrayList<>();
        if (!likedPostById.contains(postId)) likedPostById.add(postId);
    }

    public void removeLikedPost(String postId) {
        if (likedPostById != null) likedPostById.remove(postId);
    }

    public boolean hasLikedPost(String postId) {
        return likedPostById != null && likedPostById.contains(postId);
    }
}
