package github.com.tabfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2016/10/5.
 */

public class FragmentTwo extends BaseFragment {
    public static FragmentTwo startFragment() {
        return new FragmentTwo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_two, null);
    }
}
