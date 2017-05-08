package framework.network;

import framework.data.Constant;
import framework.utils.RxBus;
import rx.Subscriber;

/**
 * by y on 2016/8/7.
 */
public class NetWorkSubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        RxBus.getInstance().send(Constant.NETWORK_ERROR, e.toString());
    }

    @Override
    public void onNext(T t) {
    }

}
