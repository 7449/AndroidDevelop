package com.error.collect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btnError = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnError.setText("error");
            }
        });
    }
}
