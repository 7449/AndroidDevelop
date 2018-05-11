package com.refreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.refreshlayout.refresh.PullToRefreshView;

import static com.refreshlayout.refresh.PullToRefreshView.STATUS_REFRESH_SUCCESS;

public class MainActivity extends AppCompatActivity implements PullToRefreshView.OnRefreshListener {

    private PullToRefreshView pullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_view);
        pullToRefreshView.setOnRefreshListener(this);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        pullToRefreshView.changeStatus(PullToRefreshView.STATUS_REFRESHING);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshView.changeStatus(STATUS_REFRESH_SUCCESS);
                Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }
}
