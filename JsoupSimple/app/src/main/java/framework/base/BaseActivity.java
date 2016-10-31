package framework.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import framework.App;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
        App.getInstance().addActivity(this);
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();
}
