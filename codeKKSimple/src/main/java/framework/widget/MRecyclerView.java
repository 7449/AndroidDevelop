package framework.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * by y on 2016/8/30.
 */
public class MRecyclerView extends RecyclerView {

    private LoadingData loadingData;

    public void setLoadingData(LoadingData loadingData) {
        this.loadingData = loadingData;
    }

    public MRecyclerView(Context context) {
        super(context);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
//        if (loadingData != null) {
//            if (!canScrollVertically(-1)) {
//                loadingData.onScrolledToTop();
//            } else if (!canScrollVertically(1)) {
//                loadingData.onScrolledToBottom();
//            } else if (dy < 0) {
//                loadingData.onScrolledUp();
//            } else if (dy > 0) {
//                loadingData.onScrolledDown();
//            }
//        }

        if (loadingData != null && !canScrollVertically(1)) {
            loadingData.onScrolledToBottom();
        }

    }

    public interface LoadingData {

//        void onScrolledUp();
//
//        void onScrolledDown();
//
//        void onScrolledToTop();

        void onScrolledToBottom();

    }
}
