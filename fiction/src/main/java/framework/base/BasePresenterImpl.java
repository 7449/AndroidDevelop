package framework.base;

import com.socks.library.KLog;

import framework.App;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2017/1/8.
 */

public abstract class BasePresenterImpl<V extends BaseView<M>, M> {

    protected final V view;
    protected Observable<M> observable;
    public boolean isProgress = true;

    public BasePresenterImpl(V view) {
        this.view = view;
    }

    private NetWorkSubscriber getSubscriber() {
        return new NetWorkSubscriber();
    }

    private class NetWorkSubscriber extends Subscriber<M> {

        @Override
        public void onStart() {
            super.onStart();
            if (isProgress)
                view.showProgressBar();
        }

        @Override
        public void onCompleted() {
            if (isProgress) {
                view.hideProgressBar();
            }
            view.viewBindToLifecycle(observable);
        }


        @Override
        public void onError(Throwable e) {
            if (isProgress) {
                view.hideProgressBar();
            }
            view.netWorkError();
            view.viewBindToLifecycle(observable);
            KLog.i(e.toString());
        }

        @Override
        public void onNext(M t) {
            netWorkNext(t);
        }

    }

    protected abstract void netWorkNext(M m);


    public abstract class NetWorkOnSubscribe implements Observable.OnSubscribe<M> {
    }

    protected void startNetWork(NetWorkOnSubscribe subscriber) {
        observable = Observable.create(subscriber);
        App.unsubscribe();
        App.subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }


}