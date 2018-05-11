package scan;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import scan.listener.ScanListener;

/**
 * by y on 2017/2/15
 */

class ScanObservable implements Observable.OnSubscribe<List<AppModel>> {

    private int type = 0;
    private ScanListener listener = null;
    private boolean isNoSystem = false;

    ScanObservable(int type, ScanListener scanListener, boolean isNoSystem) {
        this.isNoSystem = isNoSystem;
        this.type = type;
        this.listener = scanListener;
    }

    @Override
    public void call(Subscriber<? super List<AppModel>> subscriber) {
        subscriber.onNext(QueryApp.getAppInfo(listener.getScanContext(), type, isNoSystem));
        subscriber.onCompleted();
    }
}
