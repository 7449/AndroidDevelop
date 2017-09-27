package com.objectbox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MainActivity extends AppCompatActivity {

    private ObjectBoxAdapter adapter;
    private Box<ObjectBoxEntity> objectBoxEntityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                adapter.addAll(objectBoxEntityBox.getAll());
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
        adapter = new ObjectBoxAdapter(new ArrayList<ObjectBoxEntity>());
        recyclerView.setAdapter(adapter);
    }


    private static class ObjectBoxAdapter extends RecyclerView.Adapter<ObjectBoxAdapter.ViewHolder> {

        private List<ObjectBoxEntity> list;

        ObjectBoxAdapter(List<ObjectBoxEntity> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (list == null) {
                return;
            }
            ObjectBoxEntity objectBoxEntity = list.get(position);
            holder.name.setText("姓名:" + objectBoxEntity.getName());
            holder.age.setText("年龄:" + objectBoxEntity.getAge());
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        void addAll(List<ObjectBoxEntity> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatTextView name;
            private AppCompatTextView age;

            ViewHolder(View itemView) {
                super(itemView);
                name = (AppCompatTextView) itemView.findViewById(R.id.name);
                age = (AppCompatTextView) itemView.findViewById(R.id.age);
            }
        }
    }
}
