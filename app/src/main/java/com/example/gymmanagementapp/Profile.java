package com.example.gymmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gymmanagementapp.pojo.BaseRequest;
import com.example.gymmanagementapp.pojo.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends BaseActivity {

    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dp = findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.person).centerCrop().circleCrop().into(dp);
        fetch();
        findViewById(R.id.logout).setOnClickListener(view -> {
            preferences.edit().clear().commit();
            Intent intent = new Intent(Profile.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK & Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void fetch() {
        api.users().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                ((TextView) findViewById(R.id.users)).setText(response.body().size() + " Users");
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });

        api.get("equipment").enqueue(new Callback<List<BaseRequest>>() {
            @Override
            public void onResponse(Call<List<BaseRequest>> call, Response<List<BaseRequest>> response) {
                ((TextView) findViewById(R.id.equips)).setText(response.body().size() + " Eqps");
            }

            @Override
            public void onFailure(Call<List<BaseRequest>> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
        api.get("workouts").enqueue(new Callback<List<BaseRequest>>() {
            @Override
            public void onResponse(Call<List<BaseRequest>> call, Response<List<BaseRequest>> response) {
                ((TextView) findViewById(R.id.workouts)).setText(response.body().size() + " Workouts");
            }

            @Override
            public void onFailure(Call<List<BaseRequest>> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }
}