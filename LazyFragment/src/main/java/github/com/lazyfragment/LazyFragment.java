package github.com.lazyfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2016/10/5.
 */

public abstract class LazyFragment extends Fragment {

    boolean isPrepared;
    boolean isLoad;
    boolean isVisible;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == view) {
            view = initView(savedInstanceState);
            isPrepared = true;
        }
        initById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    private void onVisible() {
        initData();
    }


    private void onInvisible() {
    }

    protected abstract View initView(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract void initData();

}
