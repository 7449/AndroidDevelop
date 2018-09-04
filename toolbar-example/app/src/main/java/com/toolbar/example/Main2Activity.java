package com.toolbar.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * by y.
 * <p>
 * Description:
 */
public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
//        toolbar.setOverflowIcon(null);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.main_title);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_title:
                toolbar.post(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.showOverflowMenu();
                    }
                });
                break;
            case R.id.main_title_1:
            case R.id.main_title_2:
                MenuItem item1 = toolbar.getMenu().findItem(R.id.main_title);
                AppCompatTextView viewById = item1.getActionView().findViewById(R.id.action_tv);
                viewById.setText(item.getTitle());
                toolbar.getMenu().findItem(R.id.main_title).setTitle(item.getTitle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
