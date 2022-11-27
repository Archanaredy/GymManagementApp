package com.example.gymmanagementapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.bumptech.glide.Glide;

import com.example.gymmanagementapp.pojo.AddUserRequest;

import java.io.File;
import java.net.URI;

import ir.shahabazimi.instagrampicker.InstagramPicker;
import ir.shahabazimi.instagrampicker.classes.SingleListener;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends BaseActivity {
    Button login;
    EditText name, number, age, height, weight, image;
    String url = "no image";
    ImageView dp;

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
        dp = findViewById(R.id.dp);

        login.setOnClickListener(view -> {
            if (validator.validate(name) && validator.validate(number) && validator.validate(age) && validator.validate(height) && validator.validate(weight) )
                addUser();
        });
        dp.setOnClickListener(view -> new InstagramPicker(this).show(1, 1, new SingleListener() {
            @Override
            public void selectedPic(String address) {
                uploadImage(new File(URI.create(address)));
            }
        }));
    }
    private void uploadImage(File part) {
        showLoading();
        TransferUtility transferUtility = TransferUtility.builder()
                .context(this)
                .defaultBucket("ocsimagebucket")
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                .build();

        String filename = System.currentTimeMillis() + "";
        String ext = part.getAbsolutePath().substring(part.getAbsolutePath().lastIndexOf("."));


        TransferObserver uploadObserver = transferUtility.upload(filename, part, CannedAccessControlList.PublicRead);

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                    hideLoading();
                    url = "https://ocsimagebucket.s3.amazonaws.com/" + filename;
                    Log.e("name", "https://ocsimagebucket.s3.amazonaws.com/" + filename);
                    Glide.with(getBaseContext())
                            .load("https://ocsimagebucket.s3.amazonaws.com/" + filename).centerCrop().circleCrop()
                            .into(dp);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                try {
                    Log.e("percentage", (bytesTotal / bytesCurrent) + "  %");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                makeText(ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void addUser() {
        api.create(new AddUserRequest(name.getText().toString(), number.getText().toString(), age.getText().toString(), height.getText().toString(), weight.getText().toString(), url)).enqueue(new Callback<AddUserRequest>() {
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
