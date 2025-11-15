package com.example.cictfeeds.controllers;

import com.example.cictfeeds.models.Admin;
import com.example.cictfeeds.models.Student;
import com.example.cictfeeds.utils.AppRepository;
import com.example.cictfeeds.utils.SessionManager;

public class LoginController {

    public interface LoginCallback {
        void onSuccess(Object object, String role);
        void onError(String errorMessage);
    }

    public void loginUser(String email, String password, LoginCallback callback) {
        if (email == null || email.isEmpty()) {
            callback.onError("Email cannot be empty");
            return;
        }

        if (password == null || password.isEmpty()) {
            callback.onError("Password cannot be empty");
            return;
        }

        // === Check Admin first ===
        Admin admin = AppRepository.admin;
        if (admin != null && admin.getEmail().equalsIgnoreCase(email)) {
            if (admin.getPassword().equals(password)) {
                SessionManager.loginAdmin(admin);
                callback.onSuccess(admin, "admin");
                return;
            } else {
                callback.onError("Incorrect Email or Password");
                return;
            }
        }

        // === Check Students ===
        for (Student student : AppRepository.studentList) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                if (student.getPassword().equals(password)) {
                    SessionManager.loginStudent(student);
                    callback.onSuccess(student, "student");
                    return;
                } else {
                    callback.onError("Incorrect Email or Password");
                    return;
                }
            }
        }

        // === If no match found ===
        callback.onError("Incorrect Email or Password");
    }
}
