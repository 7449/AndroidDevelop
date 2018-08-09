package com.status.layout.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.status.layout.Status;
import com.status.layout.StatusLayout;


public class MainActivity extends AppCompatActivity {

    private StatusLayout statusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusLayout = findViewById(R.id.status_root_view);
        boolean b = statusLayout.setStatus(Status.LOADING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.status_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        statusLayout.setStatus(String.valueOf(item.getTitle()));
        return true;
    }
}
