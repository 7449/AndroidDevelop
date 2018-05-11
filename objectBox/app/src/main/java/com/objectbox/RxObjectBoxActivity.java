package com.objectbox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.ErrorObserver;

/**
 * by y on 28/09/2017.
 */

public class RxObjectBoxActivity extends AppCompatActivity {

    private Adapter adapter;
    private Box<ObjectBoxEntity> objectBoxEntityBox;
    private DataSubscription observer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectbox);
        BoxStore boxStore = ObjectBoxUtils.getBoxStore();
        objectBoxEntityBox = boxStore.boxFor(ObjectBoxEntity.class);
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
                objectBoxEntityBox.put(ls);
                Toast.makeText(view.getContext(), "插入成功", Toast.LENGTH_SHORT).show();
                updateData();
            }
        });
        findViewById(R.id.btn_deleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectBoxEntityBox.removeAll();
                adapter.addAll(objectBoxEntityBox.getAll());
                Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ObjectBoxEntity> all = objectBoxEntityBox.getAll();
        if (all == null || all.isEmpty()) {
            adapter = new Adapter(new ArrayList<ObjectBoxEntity>());
        } else {
            adapter = new Adapter(all);
        }
        recyclerView.setAdapter(adapter);
    }

    private void updateData() {
        Query<ObjectBoxEntity> build = objectBoxEntityBox.query().build();
        cancel();
        observer = build.subscribe()
                .on(AndroidScheduler.mainThread())
                .onError(new ErrorObserver() {
                    @Override
                    public void onError(@NonNull Throwable th) {

                    }
                })
                .observer(new DataObserver<List<ObjectBoxEntity>>() {
                    @Override
                    public void onData(@NonNull List<ObjectBoxEntity> data) {
                        adapter.addAll(data);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancel();
    }

    private void cancel() {
        if (observer != null && !observer.isCanceled()) {
            observer.cancel();
        }
    }
}
