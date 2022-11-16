package com.example.gymmanagementapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.gymmanagementapp.pojo.BaseRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiet extends BaseActivity {

    EditText name, description, image;
    Button add;

    private String toAdd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        name = findViewById(R.id.name);
        description = findViewById(R.id.desc);
        add = findViewById(R.id.add);
        image = findViewById(R.id.image);
        add.setOnClickListener(view -> {
            if (validator.validate(name) && validator.validate(description) && validator.validate(image))
                add();
        });
        toAdd = getIntent().getStringExtra("route");
    }

    private void add() {
        api.add(toAdd, new BaseRequest(image.getText().toString(), name.getText().toString(), description.getText().toString())).enqueue(new Callback<BaseRequest>() {
            @Override
            public void onResponse(Call<BaseRequest> call, Response<BaseRequest> response) {
                makeText(toAdd + "added successfully.");
                finish();
            }

            @Override
            public void onFailure(Call<BaseRequest> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }

}
