package com.banner;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.banner.util.BannerHolder;
import com.banner.util.BannerInterface;
import com.banner.util.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BannerInterface {
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
    public ViewPager getViewPager() {
        return (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public LinearLayout getLinearLayout() {
        return (LinearLayout) findViewById(R.id.ll_points);
    }

    @Override
    public BasePagerAdapter getBannerAdapter() {
        return new PagerAdapter(mDatas);
    }

}
