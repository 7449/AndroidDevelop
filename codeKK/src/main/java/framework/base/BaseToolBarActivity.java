package framework.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codekk.p.R;

/**
 * by y on 2016/8/30.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isToolbar()) {
            Toolbar mToolbar = getView(R.id.toolbar);
            if (mToolbar == null) {
                return;
            }
            mToolbar.setTitle(toolbarTitle());
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.vector_drawable_finish);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    protected abstract boolean isToolbar();

    protected abstract String toolbarTitle();
}
