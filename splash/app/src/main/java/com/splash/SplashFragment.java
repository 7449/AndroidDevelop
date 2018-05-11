package com.splash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2017/2/8.
 */

public class SplashFragment extends Fragment {


    public static SplashFragment getSplash() {
        return new SplashFragment();
    }

    @SuppressLint({"RestrictedApi", "InflateParams"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_splash, null);
    }
}
