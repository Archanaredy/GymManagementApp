package com.example.gymmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (preferences.contains("num")) {
                if (preferences.getString("num", "").equals("9999999999")) {
                    startActivity(new Intent(FirstActivity.this, AdminDashBoard.class));
                } else {
                    startActivity(new Intent(FirstActivity.this, UserDashBoard.class));
                }
                finish();
            } else {
                startActivity(new Intent(FirstActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}