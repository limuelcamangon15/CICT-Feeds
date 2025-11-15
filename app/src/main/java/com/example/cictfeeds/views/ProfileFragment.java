package com.example.cictfeeds.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cictfeeds.R;
import com.example.cictfeeds.models.Admin;
import com.example.cictfeeds.utils.SessionManager;

import java.util.Calendar;

public class ProfileFragment extends Fragment {

    LinearLayout llUserDataContainer;
    SwitchCompat scAllowEditProfile;
    Button btnLogout, btnSaveChanges;

    TextView tvProfileName, tvProfileEmail;
    ImageView ivProfilePicture;

    EditText etPersonalFirstName, etPersonalLastName, etPersonalEmail;
    EditText etBirthday, etGenderOthers;

    AutoCompleteTextView actvGender, actvCourse, actvYearLevel, actvSpecialization;
    EditText etSection;
    private String selectedYearLevel = "";
    private boolean isDatePickerShowing = false;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view );
        setupDropdowns();
        setupDatePicker();
        setSessionData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void initializeViews(View view) {
        llUserDataContainer = view.findViewById(R.id.llUserDataContainer);
        scAllowEditProfile = view.findViewById(R.id.scAllowEditProfile);
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Profile Info
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);

        // Personal Info
        etPersonalFirstName = view.findViewById(R.id.etPersonalFirstName);
        etPersonalLastName = view.findViewById(R.id.etPersonalLastName);
        etPersonalEmail = view.findViewById(R.id.etPersonalEmail);
        etBirthday = view.findViewById(R.id.etBirthday);
        etGenderOthers = view.findViewById(R.id.etGenderOthers);
        actvGender = view.findViewById(R.id.actvGender);

        // Academic Info
        actvCourse = view.findViewById(R.id.actvCourse);
        actvYearLevel = view.findViewById(R.id.actvYearLevel);
        actvSpecialization = view.findViewById(R.id.actvSpecialization);
        etSection = view.findViewById(R.id.etSection);

        scAllowEditProfile.setOnClickListener(v -> handleAllowProfileEdit());
    }

    private void setupDropdowns() {

        String[] genders = {"Male", "Female", "Others"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, genders);
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
                requireContext(), android.R.layout.simple_dropdown_item_1line, courses);
        actvCourse.setAdapter(courseAdapter);
        actvCourse.setOnClickListener(v -> actvCourse.showDropDown());

        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, years);
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
                requireContext(), android.R.layout.simple_dropdown_item_1line, specializations);
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
                requireContext(),
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

    private void handleAllowProfileEdit(){
        boolean isChecked = scAllowEditProfile.isChecked();

        if(isChecked){
            llUserDataContainer.setBackgroundColor(getResources().getColor(R.color.lightGray50));
            btnSaveChanges.setVisibility(View.VISIBLE);

            etPersonalFirstName.setEnabled(true);
            etPersonalLastName.setEnabled(true);
            etPersonalEmail.setEnabled(true);
        }
        else{
            llUserDataContainer.setBackgroundColor(getResources().getColor(R.color.white));
            btnSaveChanges.setVisibility(View.GONE);

            etPersonalFirstName.setEnabled(false);
            etPersonalLastName.setEnabled(false);
            etPersonalEmail.setEnabled(false);
        }
    }

    private void setSessionData(){
        String firstName = SessionManager.getCurrentAdmin().getFirstName();
        String lastName = SessionManager.getCurrentAdmin().getLastName();
        String email = SessionManager.getCurrentAdmin().getEmail();

        tvProfileName.setText(firstName+" "+lastName);
        tvProfileEmail.setText(email);
        etPersonalFirstName.setText(firstName);
        etPersonalLastName.setText(lastName);
        etPersonalEmail.setText(email);
    }
}