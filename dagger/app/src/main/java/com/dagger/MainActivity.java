package com.dagger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dagger.mvp.widget.MVPActivity;
import com.dagger.singleton.SingletonActivity;

/**
 * Dagger2学习还是建议看着自动生成的代码一起学习，这样看的明白点
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_mvp).setOnClickListener(this);
        findViewById(R.id.btn_singleton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class<?> cls = null;
        switch (v.getId()) {
            case R.id.btn_mvp:
                cls = MVPActivity.class;
                break;
            case R.id.btn_singleton:
                cls = SingletonActivity.class;
                break;
        }
        Intent intent = new Intent(v.getContext(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
