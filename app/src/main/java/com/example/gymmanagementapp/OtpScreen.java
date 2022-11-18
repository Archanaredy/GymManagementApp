package com.example.gymmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.gymmanagementapp.pojo.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpScreen extends BaseActivity {
    Button login;
    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        login = findViewById(R.id.login_btn);
        number = findViewById(R.id.number);
        login.setOnClickListener(view -> {
            if (validator.validate(number, "Please enter a valid code", 4)) {
                makeText("Verification successful");
                if (preferences.getString("num", "").equals("9999999999")) {
                    startActivity(new Intent(OtpScreen.this, AdminDashBoard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                } else {
                    fetchUser();
                }
            }
        });
    }
    private void fetchUser() {
        api.getUser(preferences.getString("num", "")).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                editor.putString("id", response.body().getId()).apply();
                startActivity(new Intent(OtpScreen.this, UserDashBoard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }

}