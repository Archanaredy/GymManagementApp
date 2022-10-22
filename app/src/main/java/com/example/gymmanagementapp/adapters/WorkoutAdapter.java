package com.example.gymmanagementapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagementapp.R;
import com.example.gymmanagementapp.pojo.MenuOptionPrimary;
import com.bumptech.glide.Glide;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder>  {
    private List<MenuOptionPrimary> menuOptionList;
    private RecyclerClickListener recyclerClickListener;

    public RecyclerClickListener getRecyclerClickListener() {
        return recyclerClickListener;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    public List<MenuOptionPrimary> getMenuOptionList() {
        return menuOptionList;
    }

    public void setMenuOptionList(List<MenuOptionPrimary> menuOptionList) {
        this.menuOptionList = menuOptionList;
        notifyDataSetChanged();
    }

    public WorkoutAdapter() {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subTitle;
        private final ImageView icon;

        public MyViewHolder(final View view) {
            super(view);
            title = view.findViewById(R.id.label);
            subTitle = view.findViewById(R.id.subLabel);
            icon = view.findViewById(R.id.logo);
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(menuOptionList.get(position).getTitle());
        holder.subTitle.setText(String.valueOf(menuOptionList.get(position).getSubTitle()));
        Glide.with(holder.itemView.getContext()).load(menuOptionList.get(position).getImage()).error(R.drawable.dumbell).circleCrop().into(holder.icon);
        holder.itemView.setOnClickListener(view -> {
            recyclerClickListener.onclick(position);
        });
    }

    @Override
    public int getItemCount() {
        return menuOptionList.size();
    }
}
