package github.com.viewpagerfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private int ivWidth;
    private ViewPager viewPager;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        imageView = (ImageView) findViewById(R.id.iv_line);


        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        ivWidth = (dm.widthPixels / 3);
        viewPager.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return ViewPagerFragment.startFragment(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        lp.leftMargin = position * ivWidth + (int) (positionOffset * ivWidth);
        lp.width = ivWidth;
        imageView.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_one:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_two:
                viewPager.setCurrentItem(1);
                break;
            default:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
