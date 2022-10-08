package com.example.gymmanagementapp;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DescriptionPage extends AppCompatActivity {
    TextView name, description;
    ImageView logo;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);
        name = findViewById(R.id.label);
        description = findViewById(R.id.subLabel);
        view = findViewById(R.id.lottie);
        logo = findViewById(R.id.logo);

    }
}
