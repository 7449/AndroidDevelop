package framework.base;

/**
 * by y on 2017/2/16
 */

public abstract class BasePresenterImpl<V> {

    protected final V view;

    protected BasePresenterImpl(V view) {
        this.view = view;
    }
}
