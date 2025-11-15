package com.example.cictfeeds.controllers;

import com.example.cictfeeds.models.Student;
import com.example.cictfeeds.utils.AppRepository;

public class SignupController {

    public interface SignupCallback{
        void onSuccess(Object object);
        void onError(String errorMessage);
    }

    public void signupStudent(String firstName, String lastName, String course, int year, String email, String password, String confirmPassword, SignupCallback callback){
        if(firstName.isEmpty()){
            callback.onError("First Name cannot be empty");
            return;
        }

        if(lastName.isEmpty()){
            callback.onError("Last Name cannot be empty");
            return;
        }

        if(course.isEmpty()){
            callback.onError("Course cannot be empty");
            return;
        }

        if(year == 0){
            callback.onError("Year cannot be empty");
            return;
        }

        if(email.isEmpty()){
            callback.onError("Email cannot be empty");
            return;
        }

        if(password.isEmpty()){
            callback.onError("Password cannot be empty");
            return;
        }

        if(confirmPassword.isEmpty()){
            callback.onError("Confirm Password cannot be empty");
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            callback.onError("Please enter a valid email address");
            return;
        }

        if (password.length() < 8) {
            callback.onError("Password must be at least 8 characters long");
            return;
        }

        if (!password.matches(".*[A-Z].*")) {
            callback.onError("Password must contain at least one uppercase letter");
            return;
        }

        if (!password.matches(".*[a-z].*")) {
            callback.onError("Password must contain at least one lowercase letter");
            return;
        }

        if (!password.matches(".*[0-9].*")) {
            callback.onError("Password must contain at least one number");
            return;
        }

        if (!password.matches(".*[@#$%^&+=!].*")) {
            callback.onError("Password must contain at least one special character");
            return;
        }


        if(!password.equals(confirmPassword)){
            callback.onError("Passwords doesn't match!");
            return;
        }

        for(Student s : AppRepository.studentList){
            if(s.getEmail().equals(email)){
                callback.onError("This email is already registered");
                return;
            }
        }

        Student newStudent = new Student(firstName, lastName, course, year, email, password);
        AppRepository.addStudent(newStudent);

        callback.onSuccess(newStudent);
    }

}
