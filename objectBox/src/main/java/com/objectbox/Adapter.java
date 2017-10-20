package com.objectbox;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * by y on 28/09/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ObjectBoxEntity> list;

    Adapter(List<ObjectBoxEntity> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        ObjectBoxEntity objectBoxEntity = list.get(position);
        holder.name.setText("姓名:" + objectBoxEntity.getName());
        holder.age.setText("年龄:" + objectBoxEntity.getAge());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    void addAll(List<ObjectBoxEntity> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView name;
        private AppCompatTextView age;

        ViewHolder(View itemView) {
            super(itemView);
            name = (AppCompatTextView) itemView.findViewById(R.id.name);
            age = (AppCompatTextView) itemView.findViewById(R.id.age);
        }
    }
}