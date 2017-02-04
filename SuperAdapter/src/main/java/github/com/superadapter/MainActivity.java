package github.com.superadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.LinearLayoutManager).setOnClickListener(this);
        findViewById(R.id.GridLayoutManager).setOnClickListener(this);
        findViewById(R.id.StaggeredGridLayoutManager).setOnClickListener(this);
        findViewById(R.id.empty).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LinearLayoutManager:
                startActivity(LinearLayoutManagerActivity.class);
                break;
            case R.id.GridLayoutManager:
                startActivity(GridLayoutManagerActivity.class);
                break;
            case R.id.StaggeredGridLayoutManager:
                startActivity(StaggeredGridLayoutManagerActivity.class);
                break;
            case R.id.empty:
                startActivity(EmptyActivity.class);
                break;
        }
    }

    private void startActivity(Class<?> clz) {
        Intent intent = new Intent(getApplicationContext(), clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
