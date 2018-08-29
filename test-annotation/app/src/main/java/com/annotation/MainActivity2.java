package com.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.api.IView;
import com.api.ViewBind;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.t1)
    TextView t1;

    private ViewBind bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = IView.bind(this);
        textView.setText(R.string.app_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unBind();
    }
}