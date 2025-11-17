package com.example.cictfeeds.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.cictfeeds.MainActivity;
import com.example.cictfeeds.R;
import com.example.cictfeeds.models.Post;
import com.example.cictfeeds.utils.AppRepository;
import com.example.cictfeeds.utils.SessionManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private LinearLayout llUpcomingEvents;
    private LinearLayout llPreviousEvents;
    private  TextView tvWelcomeName;

    //upcoming week tvs
    private TextView tvFirstDayLabel, tvSecondDayLabel, tvThirdDayLabel, tvFourthDayLabel, tvFifthDayLabel, tvSixthDayLabel, tvSeventhDayLabel,
    tvFirstDayNum, tvSecondDayNum, tvThirdDayNum, tvFourthDayNum, tvFifthDayNum, tvSixthDayNum, tvSeventhDayNum,
    tvFirstDayEvent, tvSecondDayEvent, tvThirdDayEvent, tvFourthDayEvent, tvFifthDayEvent, tvSixthDayEvent, tvSeventhDayEvent;
    private ArrayList<Post> postList;
    private ArrayList<Post> postUpcomingLists;
    private String daysInAWeek[] = new String[7];
    private String numDaysInAWeek[] = new String[7];
    private int numOfEventsInAWeek[] = new int[7];



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

        //llPreviousEvents = view.findViewById(R.id.llPreviousEvents);
        llUpcomingEvents = view.findViewById(R.id.llUpcomingEvents);
        tvWelcomeName = view.findViewById(R.id.tvWelcomeName);

        tvWelcomeName.setText(SessionManager.getCurrentStudent().getFirstname());
        initializeTVUpcoming(view);

        /*
        // Populate data
        postList.add(new Post("POST20250001", "Welcome to CICT Feeds", "This is our first official post for the community!", "Announcement"));
        postList.add(new Post("POST20250002", "Tech Fair 2025", "Join us at the annual Tech Fair happening next week!", "Event"));
        postList.add(new Post("POST20250003", "Exam Schedule Released", "Final exam schedules for all departments are now posted.", "Update"));
        postList.add(new Post("POST20250004", "New Facilities Opened", "The new computer lab is now open for all CICT students.", "News"));

        renderPreviewPost(view);
        */
    }

    private void initializeTVUpcoming(View view){
        // Day of Labels
        tvFirstDayLabel = view.findViewById(R.id.tvFirstDayLabel);
        tvSecondDayLabel = view.findViewById(R.id.tvSecondDayLabel);
        tvThirdDayLabel = view.findViewById(R.id.tvThirdDayLabel);
        tvFourthDayLabel = view.findViewById(R.id.tvFourthDayLabel);
        tvFifthDayLabel = view.findViewById(R.id.tvFifthDayLabel);
        tvSixthDayLabel = view.findViewById(R.id.tvSixthDayLabel);
        tvSeventhDayLabel = view.findViewById(R.id.tvSeventhDayLabel);

        // Day of Nums
        tvFirstDayNum = view.findViewById(R.id.tvFirstDayNum);
        tvSecondDayNum = view.findViewById(R.id.tvSecondDayNum);
        tvThirdDayNum = view.findViewById(R.id.tvThirdDayNum);
        tvFourthDayNum = view.findViewById(R.id.tvFourthDayNum);
        tvFifthDayNum = view.findViewById(R.id.tvFifthDayNum);
        tvSixthDayNum = view.findViewById(R.id.tvSixthDayNum);
        tvSeventhDayNum = view.findViewById(R.id.tvSeventhDayNum);

        // Num of Events
        tvFirstDayEvent = view.findViewById(R.id.tvFirstDayEvent);
        tvSecondDayEvent = view.findViewById(R.id.tvSecondDayEvent);
        tvThirdDayEvent = view.findViewById(R.id.tvThirdDayEvent);
        tvFourthDayEvent = view.findViewById(R.id.tvFourthDayEvent);
        tvFifthDayEvent = view.findViewById(R.id.tvFifthDayEvent);
        tvSixthDayEvent = view.findViewById(R.id.tvSixthDayEvent);
        tvSeventhDayEvent = view.findViewById(R.id.tvSeventhDayEvent);

        LocalDate dateNow = LocalDate.now();

        for(int i = 0; i < daysInAWeek.length; i++){
            LocalDate dayInTheWeek = dateNow.plusDays(i);


            //day label
            DayOfWeek day = dayInTheWeek.getDayOfWeek();
            String temp =  day.toString();
            daysInAWeek[i] = temp.substring(0,3);

            //day num
            int dayNum = dayInTheWeek.getDayOfMonth();
            numDaysInAWeek[i] = String.format("%02d", dayNum);

        }

        tvFirstDayLabel.setText(daysInAWeek[0]);
        tvSecondDayLabel.setText(daysInAWeek[1]);
        tvThirdDayLabel.setText(daysInAWeek[2]);
        tvFourthDayLabel.setText(daysInAWeek[3]);
        tvFifthDayLabel.setText(daysInAWeek[4]);
        tvSixthDayLabel.setText(daysInAWeek[5]);
        tvSeventhDayLabel.setText(daysInAWeek[6]);

        tvFirstDayNum.setText(numDaysInAWeek[0]);
        tvSecondDayNum.setText(numDaysInAWeek[1]);
        tvThirdDayNum.setText(numDaysInAWeek[2]);
        tvFourthDayNum.setText(numDaysInAWeek[3]);
        tvFifthDayNum.setText(numDaysInAWeek[4]);
        tvSixthDayNum.setText(numDaysInAWeek[5]);
        tvSeventhDayNum.setText(numDaysInAWeek[6]);

        postList = AppRepository.postList;
        for(int i = 0; i < postList.size(); i++){
            Post post = postList.get(i);

            LocalDate postDate;
            if(post.getDate() instanceof java.util.Date){
                postDate = ((java.util.Date) post.getDate()).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }else{
                continue;
            }

            if(post.getTag() != null &&
            post.getTag().equals("Event") &&
                    !postDate.isBefore(dateNow) &&
                    postDate.isBefore(dateNow.plusDays(7))){

                int dayIndex = (int) ChronoUnit.DAYS.between(dateNow, postDate);
                if(dayIndex >= 0 && dayIndex < 7) {
                    numOfEventsInAWeek[dayIndex]++;
                }
            }


        }

        tvFirstDayEvent.setText(numOfEventsInAWeek[0] + " Event");
        tvSecondDayEvent.setText(numOfEventsInAWeek[1] + " Event");
        tvThirdDayEvent.setText(numOfEventsInAWeek[2] + " Event");
        tvFourthDayEvent.setText(numOfEventsInAWeek[3] + " Event");
        tvFifthDayEvent.setText(numOfEventsInAWeek[4] + " Event");
        tvSixthDayEvent.setText(numOfEventsInAWeek[5] + " Event");
        tvSeventhDayEvent.setText(numOfEventsInAWeek[6] + " Event");

        postUpcomingLists = new ArrayList<>();

        for(int j = 0; j < postList.size(); j++){
            Post post = postList.get(j);

            if(post.getTag().equals("Event") || post.getTag().equals("Announcement") ||
            post.getTag().equals("Update") || post.getTag().equals("News")){
                postUpcomingLists.add(new Post(post.getPostId(), post.getTitle(), post.getContentBody(), post.getTag()));
            }
        }

        renderPreviewPost(view);


    }


    private void renderPreviewPost(View rootView) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (Post post : postUpcomingLists) {
            // Upcoming

            if(post.getDate() == null){
                continue;
            }
            View postPreviewUpcoming = inflater.inflate(R.layout.item_post_preview, llUpcomingEvents, false);
            TextView tvTag = postPreviewUpcoming.findViewById(R.id.tvTag);
            TextView tvTitle = postPreviewUpcoming.findViewById(R.id.tvTitle);
            TextView tvExtraDetails = postPreviewUpcoming.findViewById(R.id.tvExtraDetails);
            Button btnViewDetails = postPreviewUpcoming.findViewById(R.id.btnViewDetails);


            tvTag.setText(post.getTag());
            tvTitle.setText(post.getTitle());
            tvExtraDetails.setText(post.getPostId());
            llUpcomingEvents.addView(postPreviewUpcoming);
            btnViewDetails.setOnClickListener( v -> goToFeed(post.getPostId()));

            //possibly remove this shi
            // Previous
//            View postPreviewPrevious = inflater.inflate(R.layout.item_post_preview, llPreviousEvents, false);
//            tvTag = postPreviewPrevious.findViewById(R.id.tvTag);
//            tvTitle = postPreviewPrevious.findViewById(R.id.tvTitle);
//            tvExtraDetails = postPreviewPrevious.findViewById(R.id.tvExtraDetails);
//
//            tvTag.setText(post.getTag());
//            tvTitle.setText(post.getTitle());
//            tvExtraDetails.setText(post.getPostId());
//            llPreviousEvents.addView(postPreviewPrevious);
        }
    }


    private void goToFeed(String postId){
        FeedFragment fragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("singlePostId", postId);

        MainActivity activity = (MainActivity) requireActivity();
        activity.navigateToSingleFeedPost(fragment, bundle, true);
    }
}
