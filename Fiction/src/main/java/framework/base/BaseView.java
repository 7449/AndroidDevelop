package framework.base;

import rx.Observable;

/**
 * by y on 2017/1/8.
 */

public interface BaseView<T> {


    void netWorkSuccess(T data);

    void netWorkError();

    void showProgressBar();

    void hideProgressBar();

    void viewBindToLifecycle(Observable<T> observable);
}
