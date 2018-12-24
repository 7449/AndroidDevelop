package com.sample;

import android.os.Looper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.network.RxNetWork;
import io.reactivex.network.RxNetWorkListener;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * by y.
 * <p>
 * Description:
 */
public class SampleRxJavaThread {

    interface Server {
        @GET("http://api.codekk.com/op/page/" + "{page}")
        Observable<Object> test(@Path("page") int page);
    }


    public static void start() {

        Observable<Object> test1 = RxNetWork.observable(Server.class).test(0);
        Observable<Object> test2 = RxNetWork.observable(Server.class).test(0);
        Observable<Object> test3 = RxNetWork.observable(Server.class).test(0);
        Observable<Object> test4 = RxNetWork.observable(Server.class).test(0);

        Observable<Object> objectObservable = test1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(o -> test2.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()))
                .flatMap(o -> test3.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()))
                .flatMap(o -> test4.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));

        RxNetWork
                .getInstance()
                .getApi(objectObservable, new RxNetWorkListener<Object>() {
                    @Override
                    public void onNetWorkStart() {

                    }

                    @Override
                    public void onNetWorkError(Throwable e) {
                    }

                    @Override
                    public void onNetWorkComplete() {

                    }

                    @Override
                    public void onNetWorkSuccess(Object data) {
                    }
                });
    }


    private static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
