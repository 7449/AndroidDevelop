package com.fuckapp.fragment.widget;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuckapp.R;
import com.fuckapp.fragment.model.AppModel;

import java.util.ArrayList;
import java.util.List;

import static com.fuckapp.utils.Constant.HIDE_APP;

/**
 * by y on 2016/10/31
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppModel> tempApp = new ArrayList<>();
    private OnItemClickListener onItemClickListener = null;
    private List<AppModel> appModel = new ArrayList<>();
    private SparseBooleanArray booleanArray = new SparseBooleanArray();
    private int type = -1;

    public AppAdapter(int type) {
        this.type = type;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (type != -1) {
            switch (type) {
                case HIDE_APP:
                    holder.checkBox.setVisibility(View.GONE);
                    break;
                default:
                    holder.checkBox.setVisibility(View.VISIBLE);
                    break;
            }
        }
        holder.mAppIcon.setImageDrawable(appModel.get(position).getAppIcon());
        holder.mAppLable.setText("程序名：" + appModel.get(position).getAppLabel());
        holder.mPkgName.setText("包名：" + appModel.get(position).getPkgName());
        if (booleanArray.get(position)) {
            holder.checkBox.setChecked(true);
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGray));
        } else {
            holder.checkBox.setChecked(false);
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorWhite));
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(position, appModel.get(position));
                }
            });
        }
    }

    public void refreshUI(List<AppModel> list) {
        if (list != null) {
            appModel.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void removeAll() {
        if (appModel != null) {
            appModel.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (appModel != null) {
            return appModel.size();
        }
        return 0;
    }

    public void refreshCheckBox(int position) {
        if (booleanArray.get(position)) {
            booleanArray.put(position, false);
            tempApp.remove(appModel.get(position));
        } else {
            booleanArray.put(position, true);
            tempApp.add(appModel.get(position));
        }
        notifyDataSetChanged();
    }

    public List<AppModel> getTempList() {
        return tempApp;
    }

    public void resetCheckbox() {
        if (booleanArray != null) {
            booleanArray.clear();
        }
        clearTempList();
        notifyDataSetChanged();
    }

    public void clearTempList() {
        if (tempApp != null) {
            tempApp.clear();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mAppIcon;
        TextView mAppLable;
        TextView mPkgName;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mAppIcon = (ImageView) itemView.findViewById(R.id.imgApp);
            mAppLable = (TextView) itemView.findViewById(R.id.tvAppLabel);
            mPkgName = (TextView) itemView.findViewById(R.id.tvPkgName);
            checkBox = (CheckBox) itemView.findViewById(R.id.app_check);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, AppModel appInfo);
    }
}

