package com.example.gymmanagementapp.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagementapp.R;
import com.example.gymmanagementapp.pojo.EquipmentModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.MyViewHolder>{
    private List<EquipmentModel> menuOptionList;
    private RecyclerClickListener recyclerClickListener;

    public RecyclerClickListener getRecyclerClickListener() {
        return recyclerClickListener;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    public List<EquipmentModel> getMenuOptionList() {
        return menuOptionList;
    }

    public void setMenuOptionList(List<EquipmentModel> menuOptionList) {
        this.menuOptionList = menuOptionList;
        notifyDataSetChanged();
    }

    public EquipmentAdapter() {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subTitle;
        private final ImageView icon;
        private final ImageView logo;

        public MyViewHolder(final View view) {
            super(view);
            title = view.findViewById(R.id.label);
            subTitle = view.findViewById(R.id.subLabel);
            icon = view.findViewById(R.id.status);
            logo = view.findViewById(R.id.logo);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.equi_row, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.e("equip",menuOptionList.get(position).toString());
        holder.title.setText(menuOptionList.get(position).getName());
        holder.subTitle.setText(String.valueOf(menuOptionList.get(position).getDescription()));
        Glide.with(holder.itemView.getContext()).load(menuOptionList.get(position).getImage()).error(R.drawable.dumbell).circleCrop().into(holder.logo);
        if (menuOptionList.get(position).getStatus().equals("0")) {
            Glide.with(holder.itemView.getContext()).load(R.drawable.ring_green).into(holder.icon);
        } else if (menuOptionList.get(position).getStatus().equals("1")) {
            Glide.with(holder.itemView.getContext()).load(R.drawable.ring_purple).into(holder.icon);
        } else
            Glide.with(holder.itemView.getContext()).load(R.drawable.ring_red).into(holder.icon);

        holder.itemView.setOnClickListener(view -> {
            recyclerClickListener.onclick(position);
        });
    }
    @Override
    public int getItemCount() {
        return menuOptionList.size();
    }

}
