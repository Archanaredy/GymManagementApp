package com.example.gymmanagementapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymmanagementapp.uitl.Validator;
import com.example.gymmanagementapp.uitl.api.Api;
import com.example.gymmanagementapp.uitl.api.RetrofitInstance;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;


public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    //Api class to make api call to server
    Api api;
    //validator to validate
    Validator validator = new Validator();
    //progress hud to show progress loading
    private KProgressHUD progressHUD;
    //prefs to save data in app
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //intialisation of objects
        api = RetrofitInstance.getRetrofitInstance().create(Api.class);
        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(3)
                .setDimAmount(0.1f);
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = preferences.edit();

    }

    //utility methods to show toast, show loading, hide loading progress, show and hide views
    public void makeText(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        if (!progressHUD.isShowing())
            progressHUD.show();
    }

    public void hideLoading() {
        try {
            if (progressHUD.isShowing())
                progressHUD.dismiss();
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }

    public String getId() {
        return preferences.getString("id", "");
    }

    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

}
