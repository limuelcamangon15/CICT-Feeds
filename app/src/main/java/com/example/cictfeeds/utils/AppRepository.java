package com.example.cictfeeds.utils;

import com.example.cictfeeds.models.Admin;
import com.example.cictfeeds.models.Student;
import com.example.cictfeeds.models.Post;

import java.util.ArrayList;
import java.util.Calendar;

public class AppRepository {

    // === ADMIN ===
    public static Admin admin = new Admin();

    // === STUDENTS ===
    public static ArrayList<Student> studentList = new ArrayList<>();
    private static int studentCounter = 1;

    public static void addStudent(Student student) {
        student.setStudentId(generateStudentId());
        studentList.add(student);
    }

    public static Student getStudentById(String id) {
        for (Student s : studentList) {
            if (s.getStudentId().equals(id)) return s;
        }
        return null;
    }

    public static void updateStudent(Student updatedStudent) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equals(updatedStudent.getStudentId())) {
                studentList.set(i, updatedStudent);
                break;
            }
        }
    }

    public static void deleteStudent(String id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equals(id)) {
                studentList.remove(i);
                break;
            }
        }
    }

    private static String generateStudentId() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String padded = String.format("%04d", studentCounter++); // 4 digits
        return "S" + year + padded;
    }

    // === POSTS ===
    public static ArrayList<Post> postList = new ArrayList<>();
    private static int postCounter = 1;

    public static void addPost(Post post) {
        post.setPostId(generatePostId());
        postList.add(post);
    }

    public static Post getPostById(String id) {
        for (Post p : postList) {
            if (p.getPostId().equals(id)) return p;
        }
        return null;
    }

    public static void updatePost(Post updatedPost) {
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getPostId().equals(updatedPost.getPostId())) {
                postList.set(i, updatedPost);
                break;
            }
        }
    }

    public static void deletePost(String id) {
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getPostId().equals(id)) {
                postList.remove(i);
                break;
            }
        }
    }

    private static String generatePostId() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String padded = String.format("%04d", postCounter++); // 4 digits
        return "P" + year + padded;
    }
}
