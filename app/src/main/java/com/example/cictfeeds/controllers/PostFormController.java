package com.example.cictfeeds.controllers;

import android.net.Uri;

import com.example.cictfeeds.models.Post;
import com.example.cictfeeds.utils.AppRepository;

import java.util.ArrayList;
import java.util.Date;

public class PostFormController {

    public interface PostCallback {
        void onSuccess(Post post);
        void onError(String message);
    }

    public void createPost(
            String title,
            String body,
            Date date,
            String tag,
            String location,
            ArrayList<Uri> selectedImages,
            PostCallback callback
    ) {
        // === Validation ===
        if (title == null || title.trim().isEmpty()) {
            callback.onError("Post title cannot be empty");
            return;
        }

        if (body == null || body.trim().isEmpty()) {
            callback.onError("Post content body cannot be empty");
            return;
        }

        // === Create Post object ===
        Post newPost = new Post();
            newPost.setTitle(title.trim());
            newPost.setContentBody(body.trim());
            newPost.setTag(tag != null ? tag.trim() : "");
            newPost.setLocation(location != null ? location.trim() : "");
            newPost.setDate(date);
            newPost.setCreatedAt(new Date());
            newPost.setLikes(0);

        // Convert image URIs to strings
        ArrayList<String> uriStrings = new ArrayList<>();
        if (selectedImages != null) {
            for (Uri uri : selectedImages) {
                uriStrings.add(uri.toString());
            }
        }
        newPost.setImagePaths(uriStrings);

        AppRepository.addPost(newPost);

        callback.onSuccess(newPost);
    }
}
