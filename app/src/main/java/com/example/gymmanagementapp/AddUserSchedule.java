package com.example.gymmanagementapp;

import androidx.annotation.RequiresApi;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gymmanagementapp.adapters.SpinnerAdapter;
import com.example.gymmanagementapp.pojo.AddUserScheduleRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserSchedule extends BaseActivity {

    Button add;
    TextView open, close;
    private boolean closingSelected, openingSelected, dateSelected = false;
    long selectedDateSecs, openSelected, closeSelected = 0;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_schedule);
        open = findViewById(R.id.openingtime);
        add = findViewById(R.id.add);
        add.setOnClickListener(view -> {
            if (closingSelected&&openingSelected){
                addSchedule();
            }else {
                makeText("Please select both start and end time.");
            }
        });
        close = findViewById(R.id.closetime);
        spinner = findViewById(R.id.day);
        close.setOnClickListener(view -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view12, hourOfDay, minute) -> {
                        closingSelected = true;
                        close.setText(hourOfDay + ":" + minute);
                        closeSelected = ((hourOfDay * 3600L) + (minute * 60L) + (selectedDateSecs));
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        });

        open.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view1, hourOfDay, minute) -> {
                        openingSelected = true;
                        open.setText(hourOfDay + ":" + minute);
                        openSelected = ((hourOfDay * 3600L) + (minute * 60L) + (selectedDateSecs));
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        });

        String[] years = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        spinner.setAdapter(new SpinnerAdapter(this, R.layout.spinner_tv, years));
    }

    private void addSchedule() {
        api.addSchedule(new AddUserScheduleRequest(getIntent().getStringExtra("id"), String.valueOf(openSelected), String.valueOf(closeSelected), ((TextView) spinner.getSelectedView()).getText().toString())).enqueue(new Callback<AddUserScheduleRequest>() {
            @Override
            public void onResponse(Call<AddUserScheduleRequest> call, Response<AddUserScheduleRequest> response) {
                makeText("Schedule added");
                finish();
            }

            @Override
            public void onFailure(Call<AddUserScheduleRequest> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }
}