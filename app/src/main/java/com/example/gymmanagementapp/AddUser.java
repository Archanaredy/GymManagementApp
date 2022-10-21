package com.example.gymmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUser extends BaseActivity {
    private Button login;
    EditText name, number, age, height, weight, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        login = findViewById(R.id.login_btn);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        image = findViewById(R.id.image);

        login.setOnClickListener(view -> {
            if (validator.validate(name) && validator.validate(number) && validator.validate(age) && validator.validate(height) && validator.validate(weight) && validator.validate(image))
                finish();
        });
    }
}
