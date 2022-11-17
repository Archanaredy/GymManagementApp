package com.example.gymmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class ScanActivity extends AppCompatActivity {


    private int requestCodeCameraPermission = 1001;
    private CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    private String scannedValue = "";
    SurfaceView surfaceView;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        view = findViewById(R.id.barcode_line);
        surfaceView = findViewById(R.id.cameraSurfaceView);

        if (ContextCompat.checkSelfPermission(
                getBaseContext(), android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission();
        } else {
            setupControls();
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scanner_animation);
        view.startAnimation(animation);

    }

    private void askForCameraPermission() {

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                requestCodeCameraPermission
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == requestCodeCameraPermission && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls();
            } else {
                Toast.makeText(ScanActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.stop();
    }


    private void setupControls() {
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                try {
                    //Start preview after 1s delay
                    if (ActivityCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(surfaceHolder);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            @SuppressLint("MissingPermission")
            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                try {
                    cameraSource.start(surfaceHolder);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(ScanActivity.this, "Scanner has been closed", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue;
                    //Don't forget to add this line printing value or finishing activity must run on main thread
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            cameraSource.stop();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", scannedValue);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    });

                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
//                            cameraSource.stop();
//                            postAttendance(scannedValue);
                        }
                    });
                }
            }

        });
    }


}
