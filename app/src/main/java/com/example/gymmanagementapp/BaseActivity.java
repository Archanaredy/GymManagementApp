package com.example.gymmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gymmanagementapp.pojo.fcm.FcmModel;
import com.example.gymmanagementapp.pojo.fcm.FcmResponse;
import com.example.gymmanagementapp.util.Validator;
import com.example.gymmanagementapp.util.api.Api;
import com.example.gymmanagementapp.util.api.FcmApi;
import com.example.gymmanagementapp.util.api.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        //register to fcm  notifications
        registerTopic();
//        sendMessage("Some message");

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

    //fetch fcm token
    public void fetchToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e(TAG, token);
                        Toast.makeText(BaseActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendMessage(String message) {
        RetrofitInstance.getFCmRetrofitInstance().create(FcmApi.class).sendMessage("key=AAAAmjEhhT0:APA91bHf0ttdhIukWlU7z57Y_jR3IIl2Nx09yq4FntKZSAUayleDz0BiuglQk2N2JgCAH3c3uWrub_sKHMRY5St1pEEQAgnntQEeNkTGjOFnhIMdsfj9WodzrTBVEDc32XEc5f1r3rf7", new FcmModel(new FcmModel.Data("data body", "data title"), new FcmModel.Notification("notification body", message), "/topics/welcome")).enqueue(new Callback<FcmResponse>() {
            @Override
            public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {

            }

            @Override
            public void onFailure(Call<FcmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void registerTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("welcome")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.e(TAG, msg);
//                        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
