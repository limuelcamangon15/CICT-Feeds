package com.example.cictfeeds.views;

import static com.example.cictfeeds.utils.AppRepository.deletePost;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cictfeeds.R;
import com.example.cictfeeds.models.Post;
import com.example.cictfeeds.utils.AppRepository;
import com.example.cictfeeds.utils.SessionManager;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

public class FeedFragment extends Fragment {

    TextView tvAddPost;

    ArrayList<Post> posts = AppRepository.postList;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
    LinearLayout llPostsContainer;


    public FeedFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);


        initializeViews(view);
        renderAllPosts(view);
        return view;
    }

    private void initializeViews(View view){
        tvAddPost = view.findViewById(R.id.tvAddPost);
        tvAddPost.setOnClickListener(v -> showPostForm());


        if(SessionManager.getCurrentAdmin() == null){
            tvAddPost.setVisibility(View.GONE);
        }

        llPostsContainer =view.findViewById(R.id.llPostsContainer);

    }

    private void handleDeletePost(String id){
        deletePost(id);
        refreshFeed();
    }

    private void handleUpdatePost(String id){
        Intent intent = new Intent(requireContext(), PostFormUpdateActivity.class);
        intent.putExtra("postId",id);
        startActivity(intent);
    }

    private void renderAllPosts(View view) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        llPostsContainer.removeAllViews(); // clear previous posts

        for (Post post : posts) {
            View eachPost = inflater.inflate(R.layout.item_post_onfeed, llPostsContainer, false);

            ImageButton ibDelete = eachPost.findViewById(R.id.ibDelete);
            ImageButton ibUpdate = eachPost.findViewById(R.id.ibUpdate);

            ibDelete.setOnClickListener(v -> handleDeletePost(post.getPostId()));
            ibUpdate.setOnClickListener(v -> handleUpdatePost(post.getPostId()));

            TextView tvPostTitle = eachPost.findViewById(R.id.tvPostTitle);
            TextView tvPostEventDate = eachPost.findViewById(R.id.tvPostEventDate);
            TextView tvSpacer = eachPost.findViewById(R.id.tvSpacer);
            TextView tvPostTag = eachPost.findViewById(R.id.tvPostTag);
            TextView tvLocation = eachPost.findViewById(R.id.tvLocation);
            tvLocation.setVisibility(View.GONE);
            TextView tvContentBody = eachPost.findViewById(R.id.tvContentBody);
            LinearLayout llPostImages = eachPost.findViewById(R.id.llPostImages);
            llPostImages.setVisibility(View.GONE);
            TextView tvLikeCount = eachPost.findViewById(R.id.tvLikeCount);

            tvPostTitle.setText(post.getTitle());
            if(post.getDate() != null){
                tvPostEventDate.setText(sdf.format(post.getDate()));
            }

            if(post.getDate() == null || post.getTag() == null){
                tvSpacer.setVisibility(View.GONE);
            }

            if(post.getDate() == null && post.getTag().isEmpty()){
                tvSpacer.setVisibility(View.GONE);
                tvPostEventDate.setVisibility(View.GONE);
                tvPostTag.setVisibility(View.GONE);
            }

            tvPostTag.setText(post.getTag());

            if(!post.getLocation().isEmpty()){
                tvLocation.setText(post.getLocation());
                tvLocation.setVisibility(View.VISIBLE);
            }
            tvContentBody.setText(post.getContentBody());
            tvLikeCount.setText(String.valueOf(post.getLikes()));

            // === Add runtime images ===
            llPostImages.removeAllViews();

            if(!post.getImagePaths().isEmpty()){
                llPostImages.setVisibility(View.VISIBLE);
            }

            for (String imageUriString : post.getImagePaths()) {
                ImageView imageView = new ImageView(requireContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(950, 950));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageURI(Uri.parse(imageUriString));
                llPostImages.addView(imageView);
            }

            llPostsContainer.addView(eachPost,0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFeed();
    }

    private void refreshFeed(){
        llPostsContainer.removeAllViews();
        renderAllPosts(getView());
    }

    private void showPostForm(){
        Intent intent = new Intent(requireContext(), PostFormActivity.class);
        startActivity(intent);

        getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
}