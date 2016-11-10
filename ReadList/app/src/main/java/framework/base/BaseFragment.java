package framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.trello.rxlifecycle.components.support.RxFragment;

import framework.data.Constant;
import framework.utils.RxBus;
import rx.Observable;
import rx.functions.Action1;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseFragment extends RxFragment {

    protected static final String FRAGMENT_INDEX = "fragment_index";
    protected static final String FRAGMENT_TYPE = "fragment_type";
    private View view;
    private Observable<Object> objectObservable;
    public String suffix = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = getLayoutInflater(savedInstanceState).inflate(getLayoutId(), null);
        }
        initById();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            initBundle(bundle);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        objectObservable = RxBus.getInstance().toObserverable(Constant.ONCLICK);
        objectObservable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                toolbarOnclick();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                KLog.i(" RxBus  error  unregister");
                RxBus.getInstance().unregister(Constant.ONCLICK, objectObservable);
            }
        });
        initData();
    }


    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) view.findViewById(id);
    }

    protected abstract void toolbarOnclick();

    protected abstract void initById();

    protected abstract void initData();

    protected abstract void initBundle(Bundle bundle);

    protected abstract int getLayoutId();


}

