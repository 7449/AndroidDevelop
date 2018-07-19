package com.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.api.IView;
import com.api.ViewBind;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.t1)
    TextView t1;

    @BindString(R.string.app_name)
    String name;

    @BindColor(R.color.colorAccent)
    int color;

    @BindDimen(R.dimen.simple)
    float simple;

    private ViewBind bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = IView.bind(this);
        textView.setText(name);
        textView.setTextColor(color);
        textView.setTextSize(simple);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MainFragment()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unBind();
    }
}
