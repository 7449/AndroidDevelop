package com.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.common.widget.floats.FloatingHelper;

/**
 * by y.
 * <p>
 * Description:
 */
public class SampleFloatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new FloatingHelper(R.layout.activity_float, R.layout.layout_floating_dragged).getView();
        setContentView(view);
    }
}
