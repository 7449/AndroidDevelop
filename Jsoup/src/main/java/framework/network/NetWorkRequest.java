package framework.network;

import java.util.List;

import framework.App;
import framework.base.BaseModel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/7/27.
 */
public class NetWorkRequest {

    public static void getImageList(final String url, final String type, Subscriber<List<BaseModel>> subscriber) {
        App.unsubscribe();
        App.subscription = Observable.create(new Observable.OnSubscribe<List<BaseModel>>() {
                                                 @Override
                                                 public void call(Subscriber<? super List<BaseModel>> sub) {
                                                     List<BaseModel> list = JsoupTool.getInstance().getImageList(url, type);
                                                     sub.onNext(list);
                                                     sub.onCompleted();
                                                 }
                                             }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getImageDetail(final String url, final String type, Subscriber<List<BaseModel>> subscriber) {
        App.unsubscribe();
        App.subscription = Observable.create(new Observable.OnSubscribe<List<BaseModel>>() {
                                                 @Override
                                                 public void call(Subscriber<? super List<BaseModel>> sub) {
                                                     List<BaseModel> list = JsoupTool.getInstance().getImageDetail(url, type);
                                                     sub.onNext(list);
                                                     sub.onCompleted();
                                                 }
                                             }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getVideo(final String url, Subscriber<BaseModel> subscriber) {
        App.unsubscribe();
        App.subscription = Observable.create(new Observable.OnSubscribe<BaseModel>() {
                                                 @Override
                                                 public void call(Subscriber<? super BaseModel> sub) {
                                                     BaseModel videoModel = JsoupTool.getInstance().getVideo(url);
                                                     sub.onNext(videoModel);
                                                     sub.onCompleted();
                                                 }
                                             }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
