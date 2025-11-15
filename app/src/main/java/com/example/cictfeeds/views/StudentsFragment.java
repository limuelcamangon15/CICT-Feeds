package com.example.cictfeeds.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cictfeeds.R;
import com.example.cictfeeds.adapters.StudentAdapter;
import com.example.cictfeeds.models.Student;
import com.example.cictfeeds.utils.AppRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> allStudents = AppRepository.studentList;
    private List<Student> displayedStudents;
    private Button btnPrev, btnNext;
    private TextView tvPageIndicator;

    private int itemsPerPage = 10;
    private int currentPage = 1;
    private int totalPages;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        recyclerView = view.findViewById(R.id.recyclerStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
        tvPageIndicator = view.findViewById(R.id.tvPageIndicator);

        totalPages = (int) Math.ceil((double) allStudents.size() / itemsPerPage);
        displayedStudents = new ArrayList<>();
        adapter = new StudentAdapter(displayedStudents);
        recyclerView.setAdapter(adapter);

        showPage(currentPage);

        btnPrev.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                showPage(currentPage);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentPage < totalPages) {
                currentPage++;
                showPage(currentPage);
            }
        });

        return view;
    }

    private void showPage(int page) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, allStudents.size());

        displayedStudents.clear();
        displayedStudents.addAll(allStudents.subList(start, end));
        adapter.notifyDataSetChanged();

        tvPageIndicator.setText("Page " + currentPage + " of " + totalPages);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }
}
