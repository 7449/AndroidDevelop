package com.androiddevelop.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.androiddevelop.R;
import com.androiddevelop.widget.status.StatusClickListener;
import com.androiddevelop.widget.status.StatusLayout;

/**
 * by y on 14/07/2017.
 */

public class SimpleStatusActivity extends AppCompatActivity implements View.OnClickListener, StatusClickListener {

    private StatusLayout statusLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        statusLayout = (StatusLayout) findViewById(R.id.statusLayout);

        findViewById(R.id.normal).setOnClickListener(this);
        findViewById(R.id.loading).setOnClickListener(this);
        findViewById(R.id.empty).setOnClickListener(this);
        findViewById(R.id.success).setOnClickListener(this);
        findViewById(R.id.error).setOnClickListener(this);

        statusLayout.setNorMalView(R.layout.layout_normal, null);
        statusLayout.setLoadingView(R.layout.layout_loading, null);
        statusLayout.setEmptyView(R.layout.layout_empty, null);
        statusLayout.setSuccessView(R.layout.layout_success, null);
        statusLayout.setErrorView(R.layout.layout_error, null);
        boolean b = statusLayout.setStatus(StatusLayout.Status.NORMAL);

        if (b) {
            Toast.makeText(this, "设置初始状态成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "设置失败，当前状态和设置的状态相同", Toast.LENGTH_SHORT).show();
        }
        statusLayout.setStatusClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean b = false;
        switch (v.getId()) {
            case R.id.normal:
                b = statusLayout.setStatus(StatusLayout.Status.NORMAL);
                break;
            case R.id.loading:
                b = statusLayout.setStatus(StatusLayout.Status.LOADING);
                break;
            case R.id.empty:
                b = statusLayout.setStatus(StatusLayout.Status.EMPTY);
                break;
            case R.id.success:
                b = statusLayout.setStatus(StatusLayout.Status.SUCCESS);
                break;
            case R.id.error:
                b = statusLayout.setStatus(StatusLayout.Status.ERROR);
                break;
        }
        if (b) {
            Toast.makeText(this, "设置状态成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "设置失败，当前状态和设置的状态相同", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNorMalClick() {

    }

    @Override
    public void onLoadingClick() {

    }

    @Override
    public void onEmptyClick() {
        Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessClick() {
    }

    @Override
    public void onErrorClick() {
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
    }
}

