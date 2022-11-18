package com.example.gymmanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagementapp.adapters.WorkoutAdapter;
import com.example.gymmanagementapp.pojo.BaseRequest;
import com.example.gymmanagementapp.pojo.MenuOptionPrimary;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkOutList extends BaseActivity {

    WorkoutAdapter adapter = new WorkoutAdapter();
    List<MenuOptionPrimary> menuOptionList = new ArrayList<>();
    RecyclerView menuRow;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out_list);
        menuRow = findViewById(R.id.list);
        ExtendedFloatingActionButton add;
        add = findViewById(R.id.add);
        if (!preferences.getString("num", "").equals("9999999999")) {
            hideView(add);
        }

        add.setOnClickListener(view -> {
            Intent intent = new Intent(WorkOutList.this, AddDiet.class);
            intent.putExtra("route", "workouts");
            startActivity(intent);
        });
        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    filter(s.toString());
                else {
                    adapter.setMenuOptionList(menuOptionList);

                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchDate();
    }

    private void fetchDate() {

        api.get("workouts").enqueue(new Callback<List<BaseRequest>>() {
            @Override
            public void onResponse(Call<List<BaseRequest>> call, Response<List<BaseRequest>> response) {
                menuOptionList.clear();
                for (BaseRequest user : response.body())
                    menuOptionList.add(new MenuOptionPrimary(user.getName(), user.getDescription(), user.getImage()));
                menuRow.setAdapter(adapter);
                adapter.setMenuOptionList(menuOptionList);
                adapter.setRecyclerClickListener(position -> {
                    Intent intent = new Intent(WorkOutList.this, DescriptionPage.class);
                    intent.putExtra("name", menuOptionList.get(position).getTitle());
                    intent.putExtra("desc", menuOptionList.get(position).getSubTitle());
                    intent.putExtra("image", menuOptionList.get(position).getImage());
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<List<BaseRequest>> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }


    private void filter(String text) {
        ArrayList<MenuOptionPrimary> filteredlist = new ArrayList<>();
        for (MenuOptionPrimary item : menuOptionList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setMenuOptionList(filteredlist);
        }
    }
}