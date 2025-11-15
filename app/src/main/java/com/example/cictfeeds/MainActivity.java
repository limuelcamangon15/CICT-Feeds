package com.example.cictfeeds;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cictfeeds.utils.SessionManager;
import com.example.cictfeeds.views.FeedFragment;
import com.example.cictfeeds.views.HomeFragment;
import com.example.cictfeeds.views.PostFragment;
import com.example.cictfeeds.views.ProfileFragment;
import com.example.cictfeeds.views.StudentsFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnHome, btnFeeds, btnStudents,btnPosts, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHome = findViewById(R.id.btnHome);
        btnFeeds = findViewById(R.id.btnFeeds);
        btnStudents = findViewById(R.id.btnStudents);
        btnPosts = findViewById(R.id.btnPosts);
        btnProfile = findViewById(R.id.btnProfile);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
            setActiveButton(btnHome);
        }

        btnHome.setOnClickListener(v -> {
            loadFragment(new HomeFragment(), true);
            setActiveButton(btnHome);
        });
        btnFeeds.setOnClickListener(v -> {
            loadFragment(new FeedFragment(), true);
            setActiveButton(btnFeeds);
        });
        btnStudents.setOnClickListener(v -> {
            loadFragment(new StudentsFragment(), true);
            setActiveButton(btnStudents);
        });
        btnPosts.setOnClickListener(v -> loadFragment(new PostFragment(), true));
        btnProfile.setOnClickListener(v -> {
            loadFragment(new ProfileFragment(), true);
            setActiveButton(btnProfile);
        });
    }

    private boolean isFirstFragmentLoaded = false;

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.fragmentContainer);
        String tag = fragment.getClass().getSimpleName();

        // Prevent duplicate fragment load
        if (currentFragment != null && currentFragment.getClass().getSimpleName().equals(tag)) {
            return;
        }

        if(SessionManager.getCurrentStudent()!=null){
            btnPosts.setVisibility(View.GONE);
            btnStudents.setVisibility(View.GONE);
        }

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in_right_fast,  // enter
                R.anim.slide_out_left_fast,  // exit
                R.anim.slide_in_left_fast,   // popEnter
                R.anim.slide_out_right_fast  // popExit
        );
        transaction.replace(R.id.fragmentContainer, fragment, tag);

        if (addToBackStack && isFirstFragmentLoaded) {
            transaction.addToBackStack(tag);
        }

        transaction.commit();

        isFirstFragmentLoaded = true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
        else {
            finish();
        }
    }

    private void setActiveButton(ImageButton activeButton) {
        btnHome.setSelected(false);
        btnFeeds.setSelected(false);
        btnStudents.setSelected(false);
        btnPosts.setSelected(false);
        btnProfile.setSelected(false);

        activeButton.setSelected(true);
    }

}