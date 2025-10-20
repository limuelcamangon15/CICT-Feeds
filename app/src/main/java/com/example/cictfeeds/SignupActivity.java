package com.example.cictfeeds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    Spinner spnCourse, spnYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ibig sabihin lang pag pinindot si Sign Up icacall si activity_signup(yung xml) para sya yung madisplay
            setContentView(R.layout.activity_signup);
        //

        initializeViews();
    }

    private void initializeViews(){
        spnCourse = findViewById(R.id.spnCourse);
        spnYear = findViewById(R.id.spnYear);

        String[] courses = {
                "Course",
                "BS Information Technology",
                "BS Information Systems",
                "BL Information Science"
        };

        String[] years = {"Year", "1st Year", "2nd Year", "3rd Year", "4th Year"};


        //Courses Spinner
        ArrayAdapter<String> adapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCourse.setAdapter(adapterCourses);
        spnCourse.setSelection(0);

        //Courses Year
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(adapterYear);
        spnYear.setSelection(0);

    }
}
