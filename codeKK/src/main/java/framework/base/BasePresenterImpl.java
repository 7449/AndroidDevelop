package framework.base;


/**
 * by y on 2016/8/7.
 */
public abstract class BasePresenterImpl<V> {

    public final V view;

    public BasePresenterImpl(V view) {
        this.view = view;
    }

    protected abstract void netWorkError();

}
