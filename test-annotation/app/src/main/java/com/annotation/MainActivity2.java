package com.annotation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.api.IView;
import com.api.ViewBind;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.t1)
    TextView t1;

    @BindDrawable(R.drawable.ic_launcher_background)
    Drawable launcher;

    @BindStringArray(R.array.array_string)
    String[] stringArray;

    @BindIntArray(R.array.array_int)
    int[] intArray;

    @BindClick(R.id.t1)
    public void onClickT1(View view) {
        Toast.makeText(view.getContext(), "onClickT1", Toast.LENGTH_SHORT).show();
    }

    @BindClick(R.id.t2)
    public void onClickT2(View view) {
        Toast.makeText(view.getContext(), "onClickT2", Toast.LENGTH_SHORT).show();
    }

    @BindClick(R.id.t3)
    public void onClickT3(View view) {
        Toast.makeText(view.getContext(), "onClickT3", Toast.LENGTH_SHORT).show();
    }


    @BindLongClick(R.id.t1)
    public boolean onLongClickT1(View view) {
        Toast.makeText(view.getContext(), "onLongClickT1", Toast.LENGTH_SHORT).show();
        return true;
    }

    @BindLongClick(R.id.t2)
    public boolean onLongClickT2(View view) {
        Toast.makeText(view.getContext(), "onLongClickT2", Toast.LENGTH_SHORT).show();
        return true;
    }

    @BindLongClick(R.id.t3)
    public boolean onLongClickT3(View view) {
        Toast.makeText(view.getContext(), "onLongClickT3", Toast.LENGTH_SHORT).show();
        return true;
    }

    private ViewBind bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = IView.bind(this);
        textView.setText(R.string.app_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unBind();
    }
}
