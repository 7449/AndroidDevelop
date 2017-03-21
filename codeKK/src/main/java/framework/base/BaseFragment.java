package framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import framework.data.Constant;
import framework.utils.RxBus;
import framework.utils.UIUtils;
import rx.functions.Action1;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        initById();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RxBus.getInstance()
                .toObserverable(Constant.ONCLICK)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        toolbarOnclick();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(UIUtils.getSimpleName(),throwable.getMessage());
                    }
                });
        initData();
    }

    protected <T extends View> T getView(int id) {
        return (T) view.findViewById(id);
    }

    protected abstract void initById();

    protected abstract void initData();

    protected abstract void toolbarOnclick();

    protected abstract int getLayoutId();

}

