package com.lambdasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.lambdasample.R.id.btn_test;

public class MainActivity extends AppCompatActivity {

    private Button buttonOnClick;
    private Button buttonOnLongClick;
    private Button buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOnClick = (Button) findViewById(R.id.btn_onClick);
        buttonOnLongClick = (Button) findViewById(R.id.btn_long);
        buttonTest = (Button) findViewById(btn_test);


        buttonOnClick.setOnClickListener(v -> Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show());
//        buttonOnClick.setOnClickListener(onClickListener);

        buttonOnLongClick.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), "onLong", Toast.LENGTH_SHORT).show();
            return false;
        });
//        buttonOnLongClick.setOnLongClickListener(onLongClickListener);


        /**
         * 如果项目支持Lambda 这里会直接给出提示
         */
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonTest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        this.runOnUiThread(() -> Log.i(getClass().getSimpleName(), "runOnUiThread"));
    }

    View.OnClickListener onClickListener = v -> Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show();

    View.OnLongClickListener onLongClickListener = v -> {
        Toast.makeText(v.getContext(), "onLong", Toast.LENGTH_SHORT).show();
        return false;
    };
}
