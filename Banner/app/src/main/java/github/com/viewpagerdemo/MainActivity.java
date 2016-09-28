package github.com.viewpagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import github.com.viewpagerdemo.util.BannerHolder;
import github.com.viewpagerdemo.util.BannerInterface;
import github.com.viewpagerdemo.util.BasePagerAdapter;

public class MainActivity extends AppCompatActivity implements BannerInterface<PagerModel> {
    private List<PagerModel> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatas.add(new PagerModel(R.drawable.banner1));
        mDatas.add(new PagerModel(R.drawable.banner2));
        mDatas.add(new PagerModel(R.drawable.banner3));
        mDatas.add(new PagerModel(R.drawable.banner4));
        new BannerHolder().setBanner(mDatas, this);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public ViewPager getViewPager() {
        return (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public LinearLayout getLinearLayout() {
        return (LinearLayout) findViewById(R.id.ll_points);
    }

    @Override
    public BasePagerAdapter getBannerAdapter(List<PagerModel> banner) {
        return new PagerAdapter(banner);
    }
}
