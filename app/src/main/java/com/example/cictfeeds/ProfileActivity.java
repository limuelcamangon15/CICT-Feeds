package com.example.cictfeeds;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    Context profileContext = ProfileActivity.this;

    TextView tvProfileName, tvProfileEmail;
    ImageView ivProfilePicture;

    EditText etPersonalFirstName, etPersonalLastName, etPersonalEmail;
    EditText etBirthday, etGenderOthers;

    AutoCompleteTextView actvGender, actvCourse, actvYearLevel, actvSpecialization;
    EditText etSection;

    private String selectedYearLevel = "";
    private boolean isDatePickerShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeViews();
        setupDropdowns();
        setupDatePicker();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.screen_profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        // Profile Info
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);

        // Personal Info
        etPersonalFirstName = findViewById(R.id.etPersonalFirstName);
        etPersonalLastName = findViewById(R.id.etPersonalLastName);
        etPersonalEmail = findViewById(R.id.etPersonalEmail);
        etBirthday = findViewById(R.id.etBirthday);
        etGenderOthers = findViewById(R.id.etGenderOthers);
        actvGender = findViewById(R.id.actvGender);

        // Academic Info
        actvCourse = findViewById(R.id.actvCourse);
        actvYearLevel = findViewById(R.id.actvYearLevel);
        actvSpecialization = findViewById(R.id.actvSpecialization);
        etSection = findViewById(R.id.etSection);
    }

    private void setupDropdowns() {

        String[] genders = {"Male", "Female", "Others"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, genders);
        actvGender.setAdapter(genderAdapter);

        actvGender.setOnClickListener(v -> actvGender.showDropDown());
        actvGender.setOnItemClickListener((parent, view, position, id) -> {
            String selected = genders[position];
            if (selected.equals("Others")) {
                etGenderOthers.setVisibility(View.VISIBLE);
            } else {
                etGenderOthers.setVisibility(View.GONE);
            }
        });

        String[] courses = {
                "BS Information Technology",
                "BS Information Systems",
                "BL Information Science"
        };
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, courses);
        actvCourse.setAdapter(courseAdapter);
        actvCourse.setOnClickListener(v -> actvCourse.showDropDown());

        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, years);
        actvYearLevel.setAdapter(yearAdapter);
        actvYearLevel.setOnClickListener(v -> actvYearLevel.showDropDown());

        actvYearLevel.setOnItemClickListener((parent, view, position, id) -> {
            selectedYearLevel = years[position];
            updateSpecializationDropdown(selectedYearLevel);
        });

        actvSpecialization.setOnClickListener(v -> {
            if (actvSpecialization.isEnabled()) {
                actvSpecialization.showDropDown();
            }
        });
    }

    private void updateSpecializationDropdown(String yearLevel) {
        String[] specializations;

        if (yearLevel.equals("1st Year") || yearLevel.equals("2nd Year")) {
            specializations = new String[]{"No Specialization Yet"};
            actvSpecialization.setEnabled(false);
            actvSpecialization.setText("");
            actvSpecialization.setHint("No Specialization");
        } else {
            specializations = new String[]{
                    "Web and Mobile Development",
                    "Data and Business Analytics",
                    "Infrastructure Services"
            };
            actvSpecialization.setEnabled(true);
        }

        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, specializations);
        actvSpecialization.setAdapter(specializationAdapter);
    }

    private void setupDatePicker() {
        etBirthday.setOnClickListener(v -> showDatePicker());
        etBirthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });
    }

    private void showDatePicker() {
        if (isDatePickerShowing) return;
        isDatePickerShowing = true;

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                profileContext,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%d",
                            selectedMonth + 1, selectedDay, selectedYear);
                    etBirthday.setText(date);
                    isDatePickerShowing = false;
                },
                year, month, day
        );

        datePickerDialog.setOnCancelListener(dialog -> isDatePickerShowing = false);
        datePickerDialog.setOnDismissListener(dialog -> isDatePickerShowing = false);
        datePickerDialog.show();
    }
}
