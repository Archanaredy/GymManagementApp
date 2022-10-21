package com.example.gymmanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {



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
        fetchData();
    }

    private void fetchData() {
        menuOptionList.clear();
        for (int x = 0; x < 30; x++)
            menuOptionList.add(new MenuOptionPrimary("Name " + x, "Number " + x, "image " + x));
        menuRow.setAdapter(adapter);
        adapter.setMenuOptionList(menuOptionList);
        adapter.setRecyclerClickListener(position -> {
//            startActivity(new Intent(UserList.this, UserSchedules.class).putExtra("id", response.body().get(position).getId()));
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