package com.rn.netdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.yuyh.jsonviewer.library.JsonRecyclerView;

public class NetDetailActivity extends AppCompatActivity {
    public static final String ID = "id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("数据详情");
        setContentView(R.layout.activity_net_detail);
        JsonRecyclerView jsonRecyclerView = findViewById(R.id.rv_json);
        ObjectBoxNetEntity entity = ObjectBoxUtils.getListBox().query().equal(ObjectBoxNetEntity_.id, getIntent().getLongExtra(ID, -1)).build().findUnique();
        assert entity != null;
        jsonRecyclerView.bindJson(entity.content);
    }
}
