package com.example.gymmanagementapp;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashBoard extends AppCompatActivity {
    View userList, eqList, profile, workout, diets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        userList = findViewById(R.id.userlist);
        eqList = findViewById(R.id.eqlist);
        profile = findViewById(R.id.profile);
        workout = findViewById(R.id.workout);
        diets = findViewById(R.id.diets);
        eqList.setOnClickListener(view -> startActivity(new Intent(AdminDashBoard.this, EquipmentList.class)));
        userList.setOnClickListener(view -> startActivity(new Intent(AdminDashBoard.this, UserList.class)));
        profile.setOnClickListener(view -> startActivity(new Intent(AdminDashBoard.this, Profile.class)));
        workout.setOnClickListener(view -> startActivity(new Intent(AdminDashBoard.this, WorkOutList.class)));
        diets.setOnClickListener(view ->{
            Intent intent = new Intent(AdminDashBoard.this, DietPlans.class);

            startActivity(intent);
        } );
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);

        builder
                .setPositiveButton(
                        "Yes",
                        (dialog, which) -> {
                            finish();
                        });

        builder
                .setNegativeButton(
                        "No",
                        (dialog, which) -> {
                            dialog.cancel();
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
