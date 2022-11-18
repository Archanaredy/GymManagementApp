package com.example.gymmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymmanagementapp.adapters.EquipmentAdapter;
import com.example.gymmanagementapp.pojo.EquipmentModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipmentList extends BaseActivity {

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
        if (!preferences.getString("num", "").equals("9999999999")) {
            hideView(add);
        }
        add.setOnClickListener(view -> {
            Intent intent = new Intent(EquipmentList.this, AddDiet.class);
            intent.putExtra("route", "equipment");
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
        fetchDate();
    }

    List<EquipmentModel> equipmentModels = new ArrayList<>();

    private void fetchDate() {
        api.getEquips().enqueue(new Callback<List<EquipmentModel>>() {
            @Override
            public void onResponse(Call<List<EquipmentModel>> call, Response<List<EquipmentModel>> response) {
        menuOptionList.clear();
        equipmentModels = response.body();
        menuOptionList.addAll(response.body());
        menuRow.setAdapter(adapter);
        adapter.setMenuOptionList(menuOptionList);
        adapter.setRecyclerClickListener(position -> {
//                    Intent intent = new Intent(EquipmentList.this, DescriptionPage.class);
//                    intent.putExtra("name", menuOptionList.get(position).getName());
//                    intent.putExtra("desc", menuOptionList.get(position).getDescription());
//                    startActivity(intent);
            show(menuOptionList.get(position));
        });
    }

            @Override
            public void onFailure(Call<List<EquipmentModel>> call, Throwable t) {
                makeText(t.getMessage());
            }
        });
    }

    private void show(EquipmentModel equipmentModel) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.eq_dialog, null);
        TextView name = view.findViewById(R.id.label);
        TextView des = view.findViewById(R.id.subLabel);
        ImageView logo = view.findViewById(R.id.logo);
        Button pri = view.findViewById(R.id.lock);
        name.setText(equipmentModel.getName());
        des.setText(equipmentModel.getDescription());
        Glide.with(EquipmentList.this).load(equipmentModel.getImage()).error(R.drawable.dumbell).circleCrop().into(logo);
        if (equipmentModel.getStatus().equals("0")) {
            pri.setBackground(getDrawable(R.drawable.button_bg_green));
            pri.setText("Lock/Book equipment");
        } else {
            pri.setBackground(getDrawable(R.drawable.button_bg));
            pri.setText("Unlock equipment");
        }
        pri.setOnClickListener(view1 -> {
            if (equipmentModel.getStatus().equals("0"))
                equipmentModel.setStatus("1");
            else equipmentModel.setStatus("0");
            api.update(equipmentModel.getId(), equipmentModel).enqueue(new Callback<EquipmentModel>() {
                @Override
                public void onResponse(Call<EquipmentModel> call, Response<EquipmentModel> response) {
                    fetchDate();
                    dialog.cancel();
                }

                @Override
                public void onFailure(Call<EquipmentModel> call, Throwable t) {
                    makeText(t.getMessage());
                }
            });
        });
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        dialog.show();
    }

    int LAUNCH_SECOND_ACTIVITY = 1;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                boolean isfound = false;
                for (EquipmentModel equipmentModel : equipmentModels) {
                    {
                        if (equipmentModel.getId().equals(result)) {
                            isfound = true;
                            show(equipmentModel);
                        }
                    }
                }
                if (!isfound)
                    makeText("Em scan chestunnav ra poookaaa..");

            }
        }
    }
}