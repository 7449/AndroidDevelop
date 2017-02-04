package github.com.superadapter.superAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * by y on 2016/9/29
 */

public class SuperViewHolder extends RecyclerView.ViewHolder {
    private Context context;

    public SuperViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public <T extends View> T getView(int id) {
        SparseArray<View> viewSparseArray = (SparseArray<View>) itemView.getTag();
        if (null == viewSparseArray) {
            viewSparseArray = new SparseArray<>();
            itemView.setTag(viewSparseArray);
        }
        View childView = viewSparseArray.get(id);
        if (null == childView) {
            childView = itemView.findViewById(id);
            viewSparseArray.put(id, childView);
        }
        return (T) childView;
    }

    public Context getContext() {
        return context;
    }

    public TextView getTextView(int id) {
        return getView(id);
    }

    public ImageView getImageView(int id) {
        return getView(id);
    }

    public void setTextView(int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }

    public void setTextColor(int id, int color) {
        getTextView(id).setTextColor(color);
    }

}
