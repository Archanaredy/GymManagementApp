package com.example.gymmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Profile extends BaseActivity {

    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dp = findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.person).centerCrop().circleCrop().into(dp);
        findViewById(R.id.logout).setOnClickListener(view -> {
            finish();
            finish();
        });
    }


}