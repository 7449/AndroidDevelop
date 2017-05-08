package framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2016/8/3.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected static final String FRAGMENT_URL = "url";
    protected static final String FRAGMENT_TYPE = "type";

    protected static String url = null;
    protected static String type = null;
    protected static String title = null;

    protected View view;
    protected Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = getLayoutInflater(savedInstanceState).inflate(getLayoutId(), null);
        }
        initById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected <T extends View> T getView(int id) {
        return (T) view.findViewById(id);
    }

    protected abstract void initById();

    protected abstract void initData();

    protected abstract int getLayoutId();
}
