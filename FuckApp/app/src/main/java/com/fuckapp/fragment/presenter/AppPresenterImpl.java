package com.fuckapp.fragment.presenter;

import com.fuckapp.fragment.model.AppModel;
import com.fuckapp.fragment.view.AppView;
import com.fuckapp.utils.QueryAppUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/10/31
 */

public class AppPresenterImpl implements AppPresenter {
    private Subscription subscription = null;

    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    public void nullSubscription() {
        if (subscription != null) {
            subscription = null;
        }
    }

    private AppView appView;

    public AppPresenterImpl(AppView appView) {
        this.appView = appView;
    }

    @Override
    public void startApp(final int type) {
        unsubscribe();
        subscription = Observable.create(new Observable.OnSubscribe<List<AppModel>>() {
                                             @Override
                                             public void call(Subscriber<? super List<AppModel>> sub) {
                                                 sub.onNext(QueryAppUtils.getAppInfo(type));
                                                 sub.onCompleted();
                                             }
                                         }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AppInfoSubscriber());
    }

    private class AppInfoSubscriber extends Subscriber<List<AppModel>> {

        @Override
        public void onStart() {
            super.onStart();
            appView.showProgress();
        }

        @Override
        public void onCompleted() {
            unsubscribe();
            appView.hideProgress();
            appView.obtainSuccess();
        }

        @Override
        public void onError(Throwable e) {
            unsubscribe();
            appView.hideProgress();
            appView.obtainError();
        }

        @Override
        public void onNext(List<AppModel> appModels) {
            appView.removeAllAdapter();
            appView.setAppInfo(appModels);
        }

    }
}
