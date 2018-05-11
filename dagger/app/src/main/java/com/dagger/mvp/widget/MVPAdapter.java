package com.dagger.mvp.widget;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dagger.R;
import com.dagger.mvp.model.MVPBean;

import java.util.List;

/**
 * by y on 2017/5/31.
 */

class MVPAdapter extends RecyclerView.Adapter<MVPAdapter.ViewHolder> {

    private List<MVPBean> list = null;

    MVPAdapter(List<MVPBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        MVPBean simpleModel = list.get(position);
        Glide
                .with(holder.imageView.getContext())
                .load(simpleModel.getTitleImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.imageView);
        holder.textView.setText(simpleModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;
        private AppCompatTextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.list_image);
            textView = (AppCompatTextView) itemView.findViewById(R.id.list_tv);
        }
    }

    void addAll(List<MVPBean> list) {
        if (this.list != null) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }
}
