package com.bilibilirecommend.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * by y on 2016/9/14.
 */
public abstract class BaseFragment extends Fragment {

    public Bundle mBundle = null;
    public View view = null;

    private BackHandledInterface backHandledInterface = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.backHandledInterface = (BackHandledInterface) getActivity();
        }
        mBundle = getArguments();
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandledInterface.setSelectedFragment(this);
    }

    protected <T extends View> T getView(int id) {
        return (T) view.findViewById(id);
    }

    //getLayoutInflater(savedInstanceState).inflate(fragment, null);

    public abstract boolean onBackPressed();

    public boolean isVisibility(View view) {
        return view.getVisibility() == View.GONE;
    }

    public interface BackHandledInterface {
        void setSelectedFragment(BaseFragment selectedFragment);
    }
}
