package framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import framework.data.OnToolBarClickerListener;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseFragment extends Fragment implements OnToolBarClickerListener {

    private View view;
    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        bind = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivityCreated();
    }

    @Override
    public void onToolbarClick() {

    }

    protected abstract void initActivityCreated();

    protected abstract int getLayoutId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
    }
}

