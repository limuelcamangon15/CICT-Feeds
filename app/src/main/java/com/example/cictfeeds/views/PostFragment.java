package com.example.cictfeeds.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cictfeeds.R;
import com.example.cictfeeds.adapters.PostAdapter;
import com.example.cictfeeds.adapters.StudentAdapter;
import com.example.cictfeeds.models.Post;
import com.example.cictfeeds.utils.AppRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<Post> allPosts;
    private List<Post> displayedPosts = new ArrayList<>();

    private Button btnPrev, btnNext;
    private TextView tvPageIndicator;

    private int postsPerPage = 10;
    private int currentPage = 0;
    private int totalPages;



    public PostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        recyclerView = view.findViewById(R.id.recyclerPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
        tvPageIndicator = view.findViewById(R.id.tvPageIndicator);


        loadSamplePosts();
        showPage(currentPage);

        btnPrev.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                showPage(currentPage);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                showPage(currentPage);
            }
        });

        return view;
    }

    private void loadSamplePosts(){
        allPosts = AppRepository.postList;

        totalPages = (int) Math.ceil((double) allPosts.size() / postsPerPage);
        adapter = new PostAdapter(displayedPosts);
        recyclerView.setAdapter(adapter);
    }

    private void showPage(int page) {
        int start = page * postsPerPage;
        int end = Math.min(start + postsPerPage, allPosts.size());

        displayedPosts.clear();
        displayedPosts.addAll(allPosts.subList(start, end));

        if (adapter == null) {
            adapter = new PostAdapter(displayedPosts);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        //update page indicator
        tvPageIndicator.setText("Page " + (page + 1) + " of " + totalPages);

        //disable buttons
        btnPrev.setEnabled(page > 0);
        btnNext.setEnabled(page < totalPages - 1);
    }

}