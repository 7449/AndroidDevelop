package com.sample;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
