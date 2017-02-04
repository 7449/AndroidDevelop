package framework.base;


import com.socks.library.KLog;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/8/7.
 *
 * Api If the uniform code and message,
 * can be encapsulated, the API call here more, so api's code processing separately
 */
public abstract class BasePresenterImpl<V extends BaseView<M>, M> {

    protected final V view;
    protected Observable<M> observable;
    protected boolean isClearAdapter = false;

    public BasePresenterImpl(V view) {
        this.view = view;
    }

    protected NetWorkSubscriber getSubscriber() {
        return new NetWorkSubscriber();
    }

    private class NetWorkSubscriber extends Subscriber<M> {

        @Override
        public void onStart() {
            super.onStart();
            view.showProgress();
        }

        @Override
        public void onCompleted() {
            view.hideProgress();
            view.viewBindToLifecycle(observable);
        }


        @Override
        public void onError(Throwable e) {
            KLog.i(e.toString());
            view.hideProgress();
            view.netWorkError();
            view.viewBindToLifecycle(observable);
        }

        @Override
        public void onNext(M t) {
            if (isClearAdapter) {
                view.clearAdapter();
            }
            netWorkNext(t);
        }

    }


    protected abstract void netWorkNext(M m);

    public void startNetWork(Observable<M> observable) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }
}
