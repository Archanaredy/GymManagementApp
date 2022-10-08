package com.example.gymmanagementapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddDiet extends BaseActivity {

    EditText name, description, image;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        name = findViewById(R.id.name);
        description = findViewById(R.id.desc);
        add = findViewById(R.id.add);
        image = findViewById(R.id.image);
        add.setOnClickListener(view -> {
            finish();
        });
    }

}
