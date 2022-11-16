package com.example.gymmanagementapp.util;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.gymmanagementapp.BaseActivity;
import com.example.gymmanagementapp.adapters.WorkoutAdapter;
import com.example.gymmanagementapp.pojo.AddUserScheduleRequest;
import com.example.gymmanagementapp.pojo.MenuOptionPrimary;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSchedules extends BaseActivity {

    WorkoutAdapter adapter = new WorkoutAdapter();
    List<MenuOptionPrimary> menuOptionList = new ArrayList<>();
    RecyclerView menuRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedules);
        menuRow = findViewById(R.id.list);
        ExtendedFloatingActionButton add;
        add = findViewById(R.id.add);
        if (!preferences.getString("num", "").equals("9999999999")) {
            hideView(add);
        }
        add.setOnClickListener(view -> startActivity(new Intent(UserSchedules.this, AddUserSchedule.class).putExtra("id", getIntent().getStringExtra("id"))));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDate();
    }

    private void fetchDate() {
        api.getSchedules(getIntent().getStringExtra("id")).enqueue(new Callback<List<AddUserScheduleRequest>>() {
            @Override
            public void onResponse(Call<List<AddUserScheduleRequest>> call, Response<List<AddUserScheduleRequest>> response) {
                menuOptionList.clear();
                for (AddUserScheduleRequest user : response.body()) {
                    menuOptionList.add(new MenuOptionPrimary(user.getDay(), getFormattedDate(user.getStartTime()) + "   --   " + getFormattedDate(user.getEndTime()), R.drawable.icon));
                }
                menuRow.setAdapter(adapter);
                adapter.setMenuOptionList(menuOptionList);
                adapter.setRecyclerClickListener(position -> {
                });
            }

            @Override
            public void onFailure(Call<List<AddUserScheduleRequest>> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }

    private String getFormattedDate(String time) {
        String startTime = String.format("%02d", (Long.parseLong(time) / 3600L)) + ":" + String.format("%02d", ((Long.parseLong(time) % 3600L) / 60));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date date3 = sdf.parse(startTime);
            //new format
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
            //formatting the given time to new format with AM/PM
            return sdf2.format(date3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }
}