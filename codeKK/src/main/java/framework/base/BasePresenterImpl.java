package framework.base;


import android.support.annotation.NonNull;

import com.rxnetwork.manager.RxDisposeManager;
import com.rxnetwork.manager.RxNetWork;
import com.rxnetwork.manager.RxNetWorkListener;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * by y on 2016/8/7.
 */
public abstract class BasePresenterImpl<V extends BaseView, M> implements RxNetWorkListener<M> {

    public final V view;
    private Disposable api;

    public BasePresenterImpl(V view) {
        this.view = view;
    }

    @Override
    public void onNetWorkStart() {
        view.showProgress();
    }

    @Override
    public void onNetWorkError(Throwable e) {
        netWorkError();
        if (api != null) {
            RxDisposeManager.getInstance().unDispose(api);
        }
    }

    @Override
    public void onNetWorkComplete() {
        view.hideProgress();
    }

    protected abstract void netWorkError();


    protected void netWork(@NonNull Observable<M> observable) {
        api = RxNetWork.getInstance().getApi(observable, this);
    }

}
