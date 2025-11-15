package com.example.cictfeeds.models;

import java.util.ArrayList;
import java.util.Date;

public class Post {

    private String postId;
    private String title;
    private String contentBody;
    private ArrayList<String> imagePaths;
    private String tag;
    private Date date; //optional
    private String location; //optional
    private int likes; //starts at 0
    private Date createdAt; //optional

    public Post(){}

    //complete params
    public Post(String postId, String title, String contentBody, ArrayList<String> imagePaths, String tag, Date date, String location, int likes, Date createdAt){
        this.postId = postId;
        this.title = title;
        this.contentBody = contentBody;
        this.imagePaths = imagePaths;
        this.tag = tag;
        this.date = date;
        this.location = location;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    //no location and imageUris
    public Post(String postId, String title, String contentBody, String tag, Date date, Date createdAt){
        this(postId, title, contentBody, null, tag, date, null, 0, createdAt);
    }

    //no location, imageUris and date
    public Post(String postId, String title, String contentBody, String tag, Date createdAt){
        this(postId, title, contentBody, null, tag, null, null, 0, createdAt);
    }

    //minimum required
    public Post(String postId, String title, String contentBody, String tag){
        this(postId, title, contentBody, null, tag, null, null, 0, new Date());
    }

    //test
    public Post(String postId, String title, int likes, Date createdAt){
        this(postId, title, null, null, null, null, null, likes, createdAt);
    }


    //getters and setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(ArrayList<String> paths) {
        this.imagePaths = paths;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
