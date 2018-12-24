package com.readlibrary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.common.ui.CommonFragment;

public class ReadMainFragment extends CommonFragment {

    public static ReadMainFragment newInstance() {
        return new ReadMainFragment();
    }

    @Override
    protected void initById() {

    }

    @Override
    protected void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_fragment_main;
    }
}
