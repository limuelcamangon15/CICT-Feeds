package com.example.cictfeeds.utils;

import com.example.cictfeeds.models.Admin;
import com.example.cictfeeds.models.Student;

public class SessionManager {
    private static Student currentStudent = null;
    private static Admin currentAdmin = null;


    // === STUDENT SESSION ===
    public static void loginStudent(Student student) {
        currentStudent = student;
        currentAdmin = null;
    }

    public static void logoutStudent() {
        currentStudent = null;
    }

    public static Student getCurrentStudent() {
        return currentStudent;
    }

    public static boolean isStudentSessionEmpty() {
        return currentStudent == null;
    }


    // === ADMIN SESSION ===
    public static void loginAdmin(Admin admin) {
        currentAdmin = admin;
        currentStudent = null;
    }

    public static void logoutAdmin() {
        currentAdmin = null;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static boolean isAdminSessionEmpty() {
        return currentAdmin == null;
    }



    // === UNIVERSAL SESSION HELPERS ===
    public static void logoutAll() {
        currentStudent = null;
        currentAdmin = null;
    }

    public static boolean isAnySessionActive() {
        return currentStudent != null || currentAdmin != null;
    }
}
