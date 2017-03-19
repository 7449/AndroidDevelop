package com.numberpickerview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.numberpickerview.sample.numberpicker.radio.EasyPickerListener;
import com.numberpickerview.sample.numberpicker.radio.EasyPickerView;

public class MainActivity extends AppCompatActivity implements EasyPickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEasyFragment();
            }
        });
    }

    private void initEasyFragment() {
        String[] value = new String[200];
        for (int i = 0; i < value.length; i++) {
            value[i] = String.valueOf(i);
        }
        new EasyPickerView
                .Builder(this)
                .setTextArray(value)
                .setCancelable(true)
                .setTitle("请选择身高")
                .setHintText("cm")
                .setValue(150)
                .setHintTextColor(R.color.colorPrimary)
                .setDividerColor(R.color.colorPrimary)
                .setSelectTextColor(R.color.colorPrimary)
                .show(getSupportFragmentManager(), "easy");
    }

    @Override
    public void onEasyCancel() {

    }

    @Override
    public void onEasyNext(String value) {
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
    }
}
