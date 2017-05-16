package framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.codekk.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * by y on 2016/8/7.
 */
public abstract class BaseActivity extends AppCompatActivity {


    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initCreate(savedInstanceState);
    }

    protected void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
