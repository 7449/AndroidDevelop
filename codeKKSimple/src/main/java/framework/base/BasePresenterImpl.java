package framework.base;


import android.util.Log;

import framework.data.Constant;
import framework.utils.RxBus;
import framework.utils.UIUtils;
import rx.functions.Action1;

/**
 * by y on 2016/8/7.
 */
public abstract class BasePresenterImpl<V> {

    public final V view;

    public BasePresenterImpl(V view) {
        RxBus.getInstance().toObserverable(Constant.NETWORK_ERROR).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(UIUtils.getSimpleName(),o.toString());
                netWorkError();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(UIUtils.getSimpleName(),throwable.getMessage());
            }
        });
        this.view = view;
    }

    protected abstract void netWorkError();

}
