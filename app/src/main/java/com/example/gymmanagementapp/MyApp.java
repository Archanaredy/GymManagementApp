package com.example.gymmanagementapp;

import android.app.Application;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.regions.Regions;

    public class MyApp extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    "us-east-2:5f4f5852-cc94-435c-848e-fc12dddbf9cb", // Identity pool ID
                    Regions.US_EAST_2 // Region
            );
            AWSMobileClient.getInstance().setCredentialsProvider(credentialsProvider);
        }
}
