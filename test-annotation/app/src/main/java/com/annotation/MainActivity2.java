package com.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.api.IView;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.t1)
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IView.bind(this);
        textView.setText(R.string.app_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IView.unBind(this);
    }
}
