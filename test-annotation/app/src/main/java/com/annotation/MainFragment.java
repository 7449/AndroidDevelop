package com.annotation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.api.IView;
import com.api.ViewBind;

/**
 * by y.
 * <p>
 * Description:
 */
public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.fragment_root_view)
    LinearLayout linearLayout;

    @BindString(R.string.app_name)
    String appName;

    @BindColor(R.color.colorAccent)
    int color;

    @BindDimen(R.dimen.simple)
    float simple;

    private ViewBind bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, null);
        bind = IView.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, String.valueOf(linearLayout));
        bind.unBind();
        Log.i(TAG, String.valueOf(linearLayout));
    }
}
