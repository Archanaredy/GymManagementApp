package com.archana.gym;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class EquipmentList extends AppCompatActivity {

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

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

}