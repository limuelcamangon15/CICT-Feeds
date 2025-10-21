package com.example.cictfeeds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    Context signupContext = SignupActivity.this;
    AutoCompleteTextView actvCourse, actvYear;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //ibig sabihin lang pag pinindot si Sign Up icacall si activity_signup(yung xml) para sya yung madisplay
            setContentView(R.layout.activity_signup);
        //

        initializeViews();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.screen_signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        actvCourse = findViewById(R.id.actvCourse);
        actvYear = findViewById(R.id.actvYear);
        tvLogin = findViewById(R.id.tvLogin);

        String[] courses = {
                "BS Information Technology",
                "BS Information Systems",
                "BL Information Science"
        };

        String[] years = {
                "1st Year",
                "2nd Year",
                "3rd Year",
                "4th Year"
        };

        // Adapter for courses
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                courses
        );
        actvCourse.setAdapter(courseAdapter);

        // Adapter for years
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                years
        );
        actvYear.setAdapter(yearAdapter);

        // Show dropdown when clicked
        actvCourse.setOnClickListener(v -> actvCourse.showDropDown());
        actvYear.setOnClickListener(v -> actvYear.showDropDown());

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(signupContext, LoginActivity.class);
            startActivity(intent);
        });
    }
}
