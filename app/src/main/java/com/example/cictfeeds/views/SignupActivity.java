package com.example.cictfeeds.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cictfeeds.MainActivity;
import com.example.cictfeeds.R;
import com.example.cictfeeds.controllers.SignupController;
import com.example.cictfeeds.models.Student;
import com.google.android.material.snackbar.Snackbar;

public class SignupActivity extends AppCompatActivity {

    Context signupContext = SignupActivity.this;
    AutoCompleteTextView actvCourse, actvYear;
    TextView tvLogin;
    Button btnSignUp;

    EditText etFirsName, etLastName, etEmail, etPassword, etConfirmPassword;

    SignupController signupController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
            setContentView(R.layout.activity_signup);

        initializeViews();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.screen_signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        etFirsName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        actvCourse = findViewById(R.id.actvCourse);
        actvYear = findViewById(R.id.actvYear);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvLogin = findViewById(R.id.tvLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        signupController = new SignupController();

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

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                years
        );
        actvYear.setAdapter(yearAdapter);


        //disable initially
        btnSignUp.setEnabled(false);
        btnSignUp.setAlpha(0.5f);

        //listeners for fields
        addTextWatchers();

        actvCourse.setOnClickListener(v -> actvCourse.showDropDown());
        actvYear.setOnClickListener(v -> actvYear.showDropDown());

        btnSignUp.setOnClickListener(v -> handleSignup());

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(signupContext, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void addTextWatchers() {
        android.text.TextWatcher textWatcher = new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        };

        etFirsName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);

        actvCourse.setOnItemClickListener((parent, view, position, id) -> checkFieldsForEmptyValues());
        actvYear.setOnItemClickListener((parent, view, position, id) -> checkFieldsForEmptyValues());
    }

    private void checkFieldsForEmptyValues() {
        String firstName = etFirsName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String course = actvCourse.getText().toString().trim();
        String year = actvYear.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean allFilled = !firstName.isEmpty() &&
                !lastName.isEmpty() &&
                !course.isEmpty() &&
                !year.isEmpty() &&
                !email.isEmpty() &&
                !password.isEmpty() &&
                !confirmPassword.isEmpty();

        btnSignUp.setEnabled(allFilled);
        btnSignUp.setAlpha(allFilled ? 1.0f : 0.5f);
    }



    public void handleSignup(){
        String firstName = etFirsName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String course = actvCourse.getText().toString().trim();
        String year = actvYear.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        int yearInt = 0;

        if(year.equals("1st Year")){
            yearInt = 1;
        }
        else if(year.equals("2nd Year")){
            yearInt = 2;
        }
        else if(year.equals("3rd Year")){
            yearInt = 3;
        }
        else if(year.equals("4th Year")){
            yearInt = 4;
        }


        signupController.signupStudent(firstName, lastName, course, yearInt, email, password, confirmPassword, new SignupController.SignupCallback() {
            @Override
            public void onSuccess(Object s) {
                Student student = (Student) s;

                new AlertDialog.Builder(signupContext)
                        .setTitle("Registered Successfully!")
                        .setMessage(
                                "ID: " + student.getStudentId() + "\n"
                                        + "Name: " + student.getFirstname() + " " + student.getLastname() + "\n"
                                        + "Course: " + student.getCourse() + "\n"
                                        + "Year: " + student.getYear() + "\n"
                                        + "Email: " + student.getEmail() + "\n"
                                        + "Password: " + student.getPassword()
                        )
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }

            @Override
            public void onError(String errorMessage) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.screen_signup), "", Snackbar.LENGTH_LONG);

                View snackbarView = snackbar.getView();

                GradientDrawable background = new GradientDrawable();
                background.setColor(getResources().getColor(R.color.red));
                background.setCornerRadius(20);
                snackbarView.setBackground(background);

                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(getResources().getColor(android.R.color.white));
                textView.setTextSize(16);
                textView.setMaxLines(5);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                Drawable icon = getResources().getDrawable(R.drawable.icon_warning);
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

                SpannableString spannable = new SpannableString("       " + errorMessage);
                ImageSpan imageSpan = new ImageSpan(icon, ImageSpan.ALIGN_CENTER);
                spannable.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                textView.setText(spannable);


                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                params.setMargins(32, 32, 32, 170);
                snackbarView.setLayoutParams(params);

                snackbar.show();
            }
        });
    }
}
