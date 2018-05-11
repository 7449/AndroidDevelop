package com.wheelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wheelview.interfaces.XmlPopupWindowInterface;

/**
 * 这里使用popupwindow实现弹窗效果
 * <p>
 * 如果想用DialogFragment实现可参考 NumberPickerView
 */
public class MainActivity extends AppCompatActivity implements XmlPopupWindowInterface {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XmlPopupWindow(View.inflate(getBaseContext(), R.layout.popuwindows_region, null), MainActivity.this);
            }
        });
    }

    @Override
    public void setData(String currentProviceName, String currentCityName, String currentDistrictName) {
        textView.setText(currentProviceName + "   " + currentCityName + "  " + currentDistrictName);
    }

}
