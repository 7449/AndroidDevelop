package com.greendaoexternal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * by y on 2016/12/12
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ExternalBean> list = null;


    public MainAdapter(List<ExternalBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getId()));
        holder.email.setText(String.valueOf(list.get(position).getEmail()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView email;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            email = (TextView) itemView.findViewById(R.id.tv_email);
        }
    }
}
