package com.example.cictfeeds.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cictfeeds.R;
import com.example.cictfeeds.models.Admin;
import com.example.cictfeeds.models.Student;
import com.example.cictfeeds.utils.AppRepository;
import com.example.cictfeeds.utils.Helper;
import com.example.cictfeeds.utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class ProfileFragment extends Fragment {

    LinearLayout llUserDataContainer, llBdayGenderContainer;
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
    private String[] courses = {
            "BSIT",
            "BSIS",
            "BLIS"
    };

    private String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String bday = "";
    private String course = "";
    private String section = "";
    private String specialization = "";




    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
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
        llBdayGenderContainer = view.findViewById(R.id.llBdayGenderContainer);
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

        if(SessionManager.getCurrentAdmin() != null){
            llBdayGenderContainer.setVisibility(View.GONE);
        }

        scAllowEditProfile.setOnClickListener(v -> handleAllowProfileEdit());
        btnLogout.setOnClickListener(v -> handleLogout());
        btnSaveChanges.setOnClickListener(v -> handleSaveChanges());
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


        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, courses);
        actvCourse.setAdapter(courseAdapter);
        actvCourse.setOnClickListener(v -> actvCourse.showDropDown());


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

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

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
            etBirthday.setEnabled(true);
            actvGender.setEnabled(true);
            actvCourse.setEnabled(true);
            actvYearLevel.setEnabled(true);
            etSection.setEnabled(true);
            actvSpecialization.setEnabled(true);
        }
        else{
            llUserDataContainer.setBackgroundColor(getResources().getColor(R.color.white));
            btnSaveChanges.setVisibility(View.GONE);

            etPersonalFirstName.setEnabled(false);
            etPersonalLastName.setEnabled(false);
            etPersonalEmail.setEnabled(false);
            etBirthday.setEnabled(false);
            actvGender.setEnabled(false);
            actvCourse.setEnabled(false);
            actvYearLevel.setEnabled(false);
            etSection.setEnabled(false);
            actvSpecialization.setEnabled(false);
        }
    }

    private void setSessionData(){

        Student currentStudent = SessionManager.getCurrentStudent();
        Admin currentAdmin = SessionManager.getCurrentAdmin();

        if(currentAdmin != null){
            firstName = currentAdmin.getFirstName();
            lastName = currentAdmin.getLastName();
            email = currentAdmin.getEmail();
            course = currentAdmin.getCourse();
            specialization = currentAdmin.getSpecialization();
            section = currentAdmin.getSection();
            actvYearLevel.setText(currentAdmin.getYearLevel(),false);
            actvSpecialization.setText(currentAdmin.getSpecialization(), false);
        }else if(currentStudent != null){
            firstName = currentStudent.getFirstname();
            lastName = currentStudent.getLastname();
            email = currentStudent.getEmail();
            bday = currentStudent.getBirthday();
            course = currentStudent.getCourse();
            specialization = currentStudent.getSpecialization();
            section = currentStudent.getSection();
            etBirthday.setText(bday);
            actvYearLevel.setText(years[currentStudent.getYear() - 1],false);
            if(years[currentStudent.getYear() - 1 ].equals("3rd Year") || years[currentStudent.getYear() - 1].equals("4th Year")
            && specialization != null){actvSpecialization.setText(specialization, false);}

        }

        tvProfileName.setText(firstName+" "+lastName);
        tvProfileEmail.setText(email);
        etPersonalFirstName.setText(firstName);
        etPersonalLastName.setText(lastName);
        etPersonalEmail.setText(email);
        actvCourse.setText(course, false);

        etSection.setText(section);


    }

    private void handleLogout(){

        SessionManager.logoutAll();
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);

    }

    private void handleSaveChanges(){
        //handle all changing stuff
        Student currentStudent = SessionManager.getCurrentStudent();
        Admin currentAdmin = SessionManager.getCurrentAdmin();
        if(currentStudent != null){

            currentStudent.setLastname(etPersonalLastName.getText().toString());
            currentStudent.setFirstname(etPersonalFirstName.getText().toString());
            currentStudent.setEmail(etPersonalEmail.getText().toString());
            currentStudent.setBirthday(etBirthday.getText().toString());
            currentStudent.setCourse(actvCourse.getText().toString());
            currentStudent.setSpecialization(actvSpecialization.getText().toString());
            currentStudent.setSection(etSection.getText().toString());

            if(actvGender.getText().toString().equals("Male") ||
                    actvGender.getText().toString().equals("Female")){
                currentStudent.setGender(actvGender.getText().toString());
            }else{
                currentStudent.setGender(etGenderOthers.getText().toString());
            }

            for(int i = 0; i < years.length; i++){
                currentStudent.setYear(i + 1);
            }

            AppRepository.updateStudent(currentStudent);

            showSuccessSnackbar("Profile Updated Successfully!");

        }else if(currentAdmin != null){

            currentAdmin.setFirstName(etPersonalFirstName.getText().toString());
            currentAdmin.setLastName(etPersonalLastName.getText().toString());
            currentAdmin.setEmail(etPersonalEmail.getText().toString());
            currentAdmin.setCourse(actvCourse.getText().toString());
            currentAdmin.setSpecialization(actvSpecialization.getText().toString());
            currentAdmin.setYearLevel(actvYearLevel.getText().toString());
            currentAdmin.setSection(etSection.getText().toString());

            showSuccessSnackbar("Profile Updated Successfully!");
        }


    }

    private void showSuccessSnackbar(String message) {

        Snackbar snackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();


        GradientDrawable background = new GradientDrawable();
        background.setColor(getResources().getColor(R.color.green)); // green for success
        background.setCornerRadius(20);
        snackbarView.setBackground(background);


        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(16);
        textView.setMaxLines(5);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        Drawable icon = getResources().getDrawable(R.drawable.icon_success);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

        SpannableString spannable = new SpannableString("       " + message);
        ImageSpan imageSpan = new ImageSpan(icon, ImageSpan.ALIGN_CENTER);
        spannable.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.setMargins(32, 32, 32, 170);
        snackbarView.setLayoutParams(params);

        // Show it
        snackbar.show();
    }




}