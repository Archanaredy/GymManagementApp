package com.example.gymmanagementapp.util;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserDashBoard extends BaseActivity {
    View userList, eqList, profile, workout, diets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        userList = findViewById(R.id.userlist);
        eqList = findViewById(R.id.eqlist);
        profile = findViewById(R.id.profile);
        workout = findViewById(R.id.workout);
        diets = findViewById(R.id.diets);

        userList.setOnClickListener(view -> startActivity(new Intent(UserDashBoard.this, UserSchedules.class).putExtra("id", getId())));
        eqList.setOnClickListener(view -> startActivity(new Intent(UserDashBoard.this, EquipmentList.class)));
        profile.setOnClickListener(view -> {

            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(this);
            builder.setMessage("Do you want to logout ?");
            builder.setTitle("Alert !");
            builder.setCancelable(false);

            builder
                    .setPositiveButton(
                            "Yes",
                            (dialog, which) -> {
                                editor.clear().commit();
                                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
        });
        workout.setOnClickListener(view -> startActivity(new Intent(UserDashBoard.this, WorkOutList.class)));
        diets.setOnClickListener(view -> startActivity(new Intent(UserDashBoard.this, DietPlans.class)));

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