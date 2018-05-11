package scan;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import scan.listener.ScanListener;

/**
 * by y on 2016/10/20.
 */

public class ScanAppUtils {

    public static final int ALL_APP = 1; //全部app
    public static final int SYSTEM_APP = 2; //系统app
    public static final int NO_SYSTEM_APP = 3; //第三方app

    private Subscription subscription = null;
    /**
     * true:系统app更新后也算在第三方app里面
     * false:只记录用户安装的app
     */
    private boolean isNoSystem = false;

    private static class ScanHolder {

        private static final ScanAppUtils appUtils = new ScanAppUtils();
    }

    public static ScanAppUtils newInstance() {
        return ScanHolder.appUtils;
    }

    public ScanAppUtils setNoSystem(boolean noSystem) {
        isNoSystem = noSystem;
        return this;
    }

    public void start(@ScanTypeMode final int type, final ScanListener scanListener) {
        unsubscribe();
        nullSubscription();
        subscription = Observable
                .create(new ScanObservable(type, scanListener, isNoSystem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AppModel>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (scanListener != null) {
                            scanListener.onScanStart();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        unsubscribe();
                        nullSubscription();
                    }

                    @Override
                    public void onError(Throwable e) {
                        unsubscribe();
                        nullSubscription();
                        if (scanListener != null) {
                            scanListener.onScanError(e);
                        }
                    }

                    @Override
                    public void onNext(List<AppModel> list) {
                        if (scanListener != null) {
                            scanListener.onScanSuccess(list);
                        }
                    }
                });
    }


    private void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    private void nullSubscription() {
        if (subscription != null) {
            subscription = null;
        }
    }
}
