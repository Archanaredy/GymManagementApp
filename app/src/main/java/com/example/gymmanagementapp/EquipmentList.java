package com.example.gymmanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EquipmentList extends AppCompatActivity {

    EquipmentAdapter adapter = new EquipmentAdapter();
    List<EquipmentModel> menuOptionList = new ArrayList<>();
    RecyclerView menuRow;
    EditText search;
    ImageView scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);
        menuRow = findViewById(R.id.list);
        ExtendedFloatingActionButton add;
        add = findViewById(R.id.add);

        add.setOnClickListener(view -> {
            Intent intent = new Intent(EquipmentList.this, AddDiet.class);
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
    scan = findViewById(R.id.scan);

}


    @Override
    protected void onResume() {
        super.onResume();
        fetch();
    }

    private void fetch() {
        menuOptionList.clear();
        for (int x = 0; x < 30; x++)
            menuOptionList.add(new EquipmentModel("image " + x, "Name " + x, " equipment description " + x, "0"));
        menuRow.setAdapter(adapter);
        adapter.setMenuOptionList(menuOptionList);
        adapter.setRecyclerClickListener(position -> {
//            show(menuOptionList.get(position));
        });
    }

    private void filter(String text) {
        ArrayList<EquipmentModel> filteredlist = new ArrayList<>();
        for (EquipmentModel item : menuOptionList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
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