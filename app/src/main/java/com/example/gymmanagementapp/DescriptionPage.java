package com.example.gymmanagementapp;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


public class DescriptionPage extends BaseActivity {
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
        name.setText(getIntent().getStringExtra("name"));
        description.setText(getIntent().getStringExtra("desc"));
        if (getIntent().getStringExtra("image") != null) {
            hideView(view);
            showView(logo);
            Glide.with(this).load(getIntent().getStringExtra("image")).error(R.drawable.dumbell).circleCrop().into(logo);
        }

    }
}
