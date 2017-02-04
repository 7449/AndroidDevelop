package framework.base;


import rx.Subscriber;

/**
 * by y on 2016/7/26.
 */
public abstract class BasePresenterImpl<V, M> {

    protected final V view;

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
            showProgress();
        }

        @Override
        public void onCompleted() {
            hideProgress();
        }


        @Override
        public void onError(Throwable e) {
            hideProgress();
            netWorkError();
        }

        @Override
        public void onNext(M t) {
            netWorkNext(t);
        }

    }

    protected abstract void showProgress();

    protected abstract void netWorkNext(M m);

    protected abstract void hideProgress();

    protected abstract void netWorkError();
}