package github.com.tabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private Fragment fragmentOne, fragmentTwo;

    private static final String FRAGMENT_ONE = "one";
    private static final String FRAGMENT_TWO = "two";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTabSelect(0);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_one:
                        setTabSelect(0);
                        break;
                    default:
                        setTabSelect(1);
                        break;
                }
            }
        });
    }

    private void setTabSelect(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (i) {
            case 0:
                fragmentOne = manager.findFragmentByTag(FRAGMENT_ONE);
                hideTab(transaction);
                if (null == fragmentOne) {
                    fragmentOne = FragmentOne.startFragment();
                    transaction.add(R.id.fragment, fragmentOne, FRAGMENT_ONE);
                } else {
                    transaction.show(fragmentOne);
                }
                break;
            case 1:
                fragmentTwo = manager.findFragmentByTag(FRAGMENT_TWO);
                hideTab(transaction);
                if (null == fragmentTwo) {
                    fragmentTwo = FragmentTwo.startFragment();
                    transaction.add(R.id.fragment, fragmentTwo, FRAGMENT_TWO);
                } else {
                    transaction.show(fragmentTwo);
                }
                break;
        }

        transaction.commit();
    }


    private void hideTab(FragmentTransaction transaction) {
        if (null != fragmentOne) {
            transaction.hide(fragmentOne);
        }
        if (null != fragmentTwo) {
            transaction.hide(fragmentTwo);
        }
    }

}

