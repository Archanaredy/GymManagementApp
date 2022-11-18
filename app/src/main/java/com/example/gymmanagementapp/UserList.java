package com.example.gymmanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagementapp.adapters.PrimaryMenuRecycleAdapter;
import com.example.gymmanagementapp.pojo.MenuOptionPrimary;
import com.example.gymmanagementapp.pojo.UserModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserList extends BaseActivity {

    PrimaryMenuRecycleAdapter adapter = new PrimaryMenuRecycleAdapter();
    List<MenuOptionPrimary> menuOptionList = new ArrayList<>();
    RecyclerView menuRow;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        menuRow = findViewById(R.id.list);
        ExtendedFloatingActionButton add;
        add = findViewById(R.id.add_user);
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
        add.setOnClickListener(view -> startActivity(new Intent(UserList.this, AddUser.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch();
    }

    private void fetch() {
        api.users().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
        menuOptionList.clear();
        for (UserModel user : response.body())
            menuOptionList.add(new MenuOptionPrimary(user.getName(), user.getNumber(), user.getImage()));
        menuRow.setAdapter(adapter);
        adapter.setMenuOptionList(menuOptionList);
        adapter.setRecyclerClickListener(position -> {
            startActivity(new Intent(UserList.this, UserSchedules.class).putExtra("id", response.body().get(position).getId()));
        });
    }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

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