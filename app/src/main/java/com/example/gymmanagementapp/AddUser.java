package com.example.gymmanagementapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.gymmanagementapp.pojo.AddUserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends BaseActivity {
    Button login;
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
                addUser();
        });
    }

    private void addUser() {
        api.create(new AddUserRequest(name.getText().toString(), number.getText().toString(), age.getText().toString(), height.getText().toString(), weight.getText().toString(), image.getText().toString())).enqueue(new Callback<AddUserRequest>() {
            @Override
            public void onResponse(Call<AddUserRequest> call, Response<AddUserRequest> response) {
                makeText("User added.");
                finish();
            }

            @Override
            public void onFailure(Call<AddUserRequest> call, Throwable t) {

            }
        });
    }
}
