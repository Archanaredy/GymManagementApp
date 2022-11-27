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

import com.example.gymmanagementapp.pojo.BaseRequest;
import java.io.File;
import java.net.URI;

import ir.shahabazimi.instagrampicker.InstagramPicker;
import ir.shahabazimi.instagrampicker.classes.SingleListener;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiet extends BaseActivity {

    EditText name, description, image;
    Button add;
    String url = "no image";
    ImageView dp;

    private String toAdd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        name = findViewById(R.id.name);
        description = findViewById(R.id.desc);
        add = findViewById(R.id.add);
        image = findViewById(R.id.image);
        dp = findViewById(R.id.dp);
        add.setOnClickListener(view -> {
            if (validator.validate(name) && validator.validate(description) && validator.validate(image))
                add();
        });
        toAdd = getIntent().getStringExtra("route");
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

    private void add() {
        api.add(toAdd, new BaseRequest(url, name.getText().toString(), description.getText().toString())).enqueue(new Callback<BaseRequest>() {


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
