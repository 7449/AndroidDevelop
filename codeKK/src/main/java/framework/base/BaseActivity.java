package framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
    }


    public static Activity getActivity() {
        return activity;
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();
}
