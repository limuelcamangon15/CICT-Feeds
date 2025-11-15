package com.example.cictfeeds.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.cictfeeds.R;
import com.example.cictfeeds.models.Post;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private LinearLayout llUpcomingEvents;
    private LinearLayout llPreviousEvents;
    private ArrayList<Post> postList;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views properly
        llPreviousEvents = view.findViewById(R.id.llPreviousEvents);
        llUpcomingEvents = view.findViewById(R.id.llUpcomingEvents);

        postList = new ArrayList<>();

        // Populate data
        postList.add(new Post("POST20250001", "Welcome to CICT Feeds", "This is our first official post for the community!", "Announcement"));
        postList.add(new Post("POST20250002", "Tech Fair 2025", "Join us at the annual Tech Fair happening next week!", "Event"));
        postList.add(new Post("POST20250003", "Exam Schedule Released", "Final exam schedules for all departments are now posted.", "Update"));
        postList.add(new Post("POST20250004", "New Facilities Opened", "The new computer lab is now open for all CICT students.", "News"));

        renderPreviewPost(view);

    }

    private void renderPreviewPost(View rootView) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (Post post : postList) {
            // Upcoming
            View postPreviewUpcoming = inflater.inflate(R.layout.item_post_preview, llUpcomingEvents, false);
            TextView tvTag = postPreviewUpcoming.findViewById(R.id.tvTag);
            TextView tvTitle = postPreviewUpcoming.findViewById(R.id.tvTitle);
            TextView tvExtraDetails = postPreviewUpcoming.findViewById(R.id.tvExtraDetails);

            tvTag.setText(post.getTag());
            tvTitle.setText(post.getTitle());
            tvExtraDetails.setText(post.getPostId());
            llUpcomingEvents.addView(postPreviewUpcoming);

            // Previous
            View postPreviewPrevious = inflater.inflate(R.layout.item_post_preview, llPreviousEvents, false);
            tvTag = postPreviewPrevious.findViewById(R.id.tvTag);
            tvTitle = postPreviewPrevious.findViewById(R.id.tvTitle);
            tvExtraDetails = postPreviewPrevious.findViewById(R.id.tvExtraDetails);

            tvTag.setText(post.getTag());
            tvTitle.setText(post.getTitle());
            tvExtraDetails.setText(post.getPostId());
            llPreviousEvents.addView(postPreviewPrevious);
        }
    }
}
