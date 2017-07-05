package com.greendao.multitable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.greendao.multitable.one.GreenDao1Activity;
import com.greendao.multitable.two.GreenDao2Activity;

/**
 * 由于关联查询是懒加载，会优先从缓存中处理，
 * 所以更新数据和删除数据 可以使用 resetXXX()  重置一对多的关系，使下一个获取调用查询一个新的结果。
 *
 * 有时表没有使用ToOne或者ToMany建立关系，这时可以用 greendao 的多表查询  queryBuilder.join()  来实现多表查询
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                start(GreenDao1Activity.class);
                break;
            case R.id.button2:
                start(GreenDao2Activity.class);
                break;
        }
    }


    private void start(Class<?> c) {
        Intent intent = new Intent(this, c);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
