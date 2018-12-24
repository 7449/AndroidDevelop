package xyz.viseator.anonymouscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import xyz.viseator.anonymouscard.R;

/**
 * Created by yanhao on 16-12-24.
 */

public class BeginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_begin);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(BeginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },2000);

    }
}
