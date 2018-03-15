package com.toolbar.example;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private AppCompatTextView left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MainAdapter());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        right.setTranslationX(Math.abs(verticalOffset * 160) / appBarLayout.getTotalScrollRange());
        left.setTranslationY(-Math.abs(verticalOffset * 60) / appBarLayout.getTotalScrollRange());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private static class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


        private List<String> list = null;

        MainAdapter() {
            list = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                list.add("item  + " + i);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private AppCompatTextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.main_tv);
            }
        }
    }
}
