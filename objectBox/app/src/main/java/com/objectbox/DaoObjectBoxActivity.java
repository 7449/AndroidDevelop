package com.objectbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.daocompat.query.Query;

/**
 * by y on 28/09/2017.
 */

public class DaoObjectBoxActivity extends AppCompatActivity {
    private Adapter adapter;
    private ObjectBoxEntityDao objectBoxEntityDao;
    private Query<ObjectBoxEntity> build;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectbox);

        DaoSession dao = ObjectBoxUtils.getDao();

        objectBoxEntityDao = dao.getObjectBoxEntityDao();
        build = objectBoxEntityDao.queryBuilder().build();

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ObjectBoxEntity> ls = new ArrayList<>();
                ls.add(new ObjectBoxEntity("王一", 1));
                ls.add(new ObjectBoxEntity("王二", 2));
                ls.add(new ObjectBoxEntity("王三", 3));
                ls.add(new ObjectBoxEntity("王四", 4));
                ls.add(new ObjectBoxEntity("王五", 5));
                ls.add(new ObjectBoxEntity("王六", 6));
                ls.add(new ObjectBoxEntity("王七", 7));
                ls.add(new ObjectBoxEntity("王八", 8));
                ls.add(new ObjectBoxEntity("王九", 9));
                ls.add(new ObjectBoxEntity("王十", 10));
                objectBoxEntityDao.insertInTx(ls);
                Toast.makeText(view.getContext(), "插入成功", Toast.LENGTH_SHORT).show();
                adapter.addAll(build.find());
            }
        });
        findViewById(R.id.btn_deleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectBoxEntityDao.deleteAll();
                adapter.addAll(build.find());
                Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ObjectBoxEntity> all = build.find();
        if (all == null || all.isEmpty()) {
            adapter = new Adapter(new ArrayList<ObjectBoxEntity>());
        } else {
            adapter = new Adapter(all);
        }
        recyclerView.setAdapter(adapter);
    }
}
