package framework.base;


import rx.Observable;

/**
 * by y on 2016/8/7.
 */
public interface BaseView<T> {

    void clearAdapter();

    void netWorkError();

    void showProgress();

    void hideProgress();

    void viewBindToLifecycle(Observable<T> observable);

    void errorMessage(String msg);

}
