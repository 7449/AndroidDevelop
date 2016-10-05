package github.com.lazyfragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * by y on 2016/10/5.
 */

public class TestFragment extends LazyFragment {

    private static final String FRAGMENT_INDEX = "fragment_index";
    private TextView tv;
    private ProgressBar progressBar;
    private int index = 0;

    public static TestFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        TestFragment fragment = new TestFragment();
        bundle.putInt(FRAGMENT_INDEX, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(FRAGMENT_INDEX);
        }
    }

    @Override
    protected View initView(Bundle savedInstanceState) {
        return getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_test, null);
    }

    @Override
    protected void initById() {
        tv = (TextView) view.findViewById(R.id.tv);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }

        //这里加载数据
        progressBar();

        isLoad = true;
    }

    private void progressBar() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                switchId(index);
            }
        }, 1000);
    }


    private void switchId(int id) {
        switch (id) {
            case 0:
                tv.setText("界面一");
                break;
            case 1:
                tv.setText("界面二");
                break;
            case 2:
                tv.setText("界面三");
                break;
            case 3:
                tv.setText("界面四");
                break;
            case 4:
                tv.setText("界面五");
                break;
            case 5:
                tv.setText("界面六");
                break;
            case 6:
                tv.setText("界面七");
                break;
        }

    }
}
