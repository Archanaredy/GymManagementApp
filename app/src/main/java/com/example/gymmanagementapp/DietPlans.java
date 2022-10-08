package com.archana.gym;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;


public class DietPlans extends BaseActivity {


    RecyclerView menuRow;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plans);
        menuRow = findViewById(R.id.list);
        ExtendedFloatingActionButton add;
        add = findViewById(R.id.add);

        add.setOnClickListener(view -> {
            Intent intent = new Intent(DietPlans.this, AddDiet.class);
            startActivity(intent);
        });
        search = findViewById(R.id.search);
    }
}