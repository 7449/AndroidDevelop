package github.com.superadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * by y on 2016/9/30
 */

public abstract class BaseActivity extends AppCompatActivity {

    public View getView(int id) {
        return LayoutInflater.from(this).inflate(id, (ViewGroup) findViewById(android.R.id.content), false);
    }

    public void Toast(Object o) {
        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();
}
