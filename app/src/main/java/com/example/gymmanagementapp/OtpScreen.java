package com.example.gymmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OtpScreen extends BaseActivity {
    private Button login;
    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        login = findViewById(R.id.login_btn);
        number = findViewById(R.id.number);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validator.validate(number)) {
                    startActivity(new Intent(OtpScreen.this, AdminDashBoard.class));
                }

            }
        });
    }
}