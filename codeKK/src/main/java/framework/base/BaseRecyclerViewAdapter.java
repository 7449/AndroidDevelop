package framework.base;

import android.annotation.SuppressLint;
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
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnLongClickListener;
    private List<T> mDatas = new LinkedList<>();

    public BaseRecyclerViewAdapter(List<T> mDatas) {
        if (null != mDatas && !mDatas.isEmpty()) {
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

    public void remove(int position) {
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatas.size() != 0) {
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false));
    }

    protected abstract int getItemLayoutId();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final T data = mDatas.get(position);
        if (data == null) {
            return;
        }
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        onBind(baseViewHolder.getViewHolder(), position, data);
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

    protected abstract void onBind(ViewHolder holder, int position, T data);

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private ViewHolder viewHolder;

        public BaseViewHolder(View itemView) {
            super(itemView);
            viewHolder = ViewHolder.getViewHolder(itemView);
        }

        public ViewHolder getViewHolder() {
            return viewHolder;
        }
    }

    public static class ViewHolder {
        private SparseArray<View> viewHolder;
        private View view;

        private ViewHolder(View view) {
            this.view = view;
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }

        public <T extends View> T get(int id) {
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }

        public static ViewHolder getViewHolder(View view) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }
            return viewHolder;
        }

        public View getConvertView() {
            return view;
        }

        public TextView getTextView(int id) {
            return get(id);
        }

        public ImageView getImageView(int id) {
            return get(id);
        }

        public void setTextView(int id, CharSequence charSequence) {
            getTextView(id).setText(charSequence);
        }
    }
}
