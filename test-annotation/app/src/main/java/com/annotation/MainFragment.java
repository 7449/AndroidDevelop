package com.annotation;

import android.graphics.drawable.Drawable;
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

import java.util.Arrays;

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

    @BindDrawable(R.drawable.ic_launcher_background)
    Drawable launcher;

    @BindStringArray(R.array.array_string)
    String[] stringArray;

    @BindIntArray(R.array.array_int)
    int[] intArray;

    private ViewBind bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, null);
        bind = IView.bind(this, inflate);
        Log.i(TAG, String.valueOf(launcher));
        Log.i(TAG, Arrays.toString(stringArray));
        Log.i(TAG, Arrays.toString(intArray));
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
