package com.example.cictfeeds.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cictfeeds.MainActivity;
import com.example.cictfeeds.R;
import com.example.cictfeeds.controllers.LoginController;
import com.example.cictfeeds.models.Student;
import com.google.android.material.snackbar.Snackbar;
import com.example.cictfeeds.utils.Helper;

public class LoginActivity extends AppCompatActivity {

    Context loginContext = LoginActivity.this;

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvSignUp;

    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initializeLogin();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.screen_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeLogin(){
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        loginController = new LoginController();

        btnLogin.setEnabled(false);
        btnLogin.setAlpha(0.5f);

        addTextWatchers();

        btnLogin.setOnClickListener(v -> handleLogin());
        tvSignUp.setOnClickListener(v -> handleSignup());
    }

    private void addTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }

    private void checkFieldsForEmptyValues() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        boolean allFilled = !email.isEmpty() && !password.isEmpty();

        btnLogin.setEnabled(allFilled);
        btnLogin.setAlpha(allFilled ? 1.0f : 0.5f);
    }

    private void handleLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
   
        loginController.loginUser(email, password, new LoginController.LoginCallback() {
            @Override
            public void onSuccess(Object object, String role) {

                if(role.equalsIgnoreCase("admin")){
                    Intent intent = new Intent(loginContext, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Helper.showSucccessSnackbar(findViewById(R.id.screen_login), "Logged In success for student");
                }
            }

            @Override
            public void onError(String errorMessage) {
                Helper.showErrorSnackbar(findViewById(R.id.screen_login), errorMessage);
            }
        });
    }

    private void handleSignup(){
        Helper.goToSignUpScreen(loginContext);
    }
}