package framework.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/8/7.
 */
@SuppressWarnings("ALL")
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnLongClickListener;
    private List<T> mDatas = new LinkedList<>();

    public BaseRecyclerViewAdapter(List<T> mDatas) {
        if (mDatas != null) {
            this.mDatas = mDatas;
        }
    }


    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T info);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
    }


    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }

    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatas.size() != 0) {
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }


    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final T data = mDatas.get(position);
        if (data == null) {
            return;
        }
        onBind((BaseViewHolder) holder, position, data);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position, data);
                }
            });
        }
        if (mOnLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongClickListener.onLongClick(v, position, data);
                    return true;
                }
            });
        }
    }

    protected abstract int getItemLayoutId();

    protected abstract void onBind(BaseViewHolder holder, int position, T data);


    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
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
}
