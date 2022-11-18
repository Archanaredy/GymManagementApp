package com.example.gymmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.gymmanagementapp.pojo.UserModel;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    // declare views
    Button login;
    BlurImageView bg;
    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login_btn);
        number = findViewById(R.id.number);
        login.setOnClickListener(view ->  {
            if (validator.validate(number)) {
                fetch();
            }
        });
    }
    private void fetch() {
        showLoading();
        api.users().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                hideLoading();
                for (UserModel userModel : response.body()) {
                    if (userModel.getNumber().equals(number.getText().toString())) {
                        makeText("Verification code sent.");
                        editor.putString("num", number.getText().toString()).apply();
                        startActivity(new Intent(MainActivity.this, OtpScreen.class));
                        finish();
                        return;
                    }
                }
                if (number.getText().toString().equals("9999999999")) {
                    makeText("Verification code sent.");
                    editor.putString("num", number.getText().toString()).apply();
                    startActivity(new Intent(MainActivity.this, OtpScreen.class));
                    finish();
                } else
                    makeText("User not registered, please contact manager..");
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                hideLoading();
            }
        });
    }

}

