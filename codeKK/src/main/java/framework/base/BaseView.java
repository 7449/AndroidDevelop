package framework.base;


import java.util.List;

/**
 * by y on 2016/8/7.
 */
public interface BaseView {
    void netWorkError();

    void showProgress();

    void hideProgress();

    interface BaseListView<T> extends BaseView {
        void setData(List<T> projectArray);

        void noMore();
    }
}
