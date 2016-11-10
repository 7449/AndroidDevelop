package framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.readlist.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import framework.App;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
        App.getInstance().addActivity(this);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();

}
