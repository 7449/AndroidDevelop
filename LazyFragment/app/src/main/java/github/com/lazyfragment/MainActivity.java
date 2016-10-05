package github.com.lazyfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        mData = new ArrayList<>();
        mData.add("一");
        mData.add("二");
        mData.add("三");
        mData.add("四");
        mData.add("五");
        mData.add("六");
        mData.add("七");
        TabNameAdapter tabNameAdapter = new TabNameAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabNameAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class TabNameAdapter extends FragmentPagerAdapter {


        public TabNameAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }
}
