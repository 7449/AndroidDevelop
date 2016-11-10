package framework.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import framework.utils.UIUtils;

/**
 * by y on 2016/11/9
 */
public abstract class BaseDialog extends AlertDialog {


    private View view;

    protected BaseDialog(@NonNull Context context) {
        super(context);
        view = UIUtils.getInflate(getContext(), getLayoutId());
        setView(view);
        setTitle(getTitle());
        initById();
        initCreate();
        show();
    }

    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) view.findViewById(id);
    }

    protected abstract void initCreate();

    protected abstract void initById();

    protected abstract String getTitle();

    protected abstract int getLayoutId();
}
