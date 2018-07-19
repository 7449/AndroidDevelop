package com.edit.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.edit.image.view.EditImageLayout;

public class MainActivity extends AppCompatActivity {

    private EditImageLayout editImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editImageLayout = findViewById(R.id.edit_image);
        String savePath = FileUtils.getDiskFileDir(this, "img.jpg").getPath();
        FileUtils.delete(savePath);
        Glide
                .with(this)
                .asBitmap()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .load("http://p.ssl.qhimg.com/t01078f558434804905.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        editImageLayout.setBitmap(resource);
                    }
                });
        findViewById(R.id.text).setOnClickListener(v -> editImageLayout.start(EditImageLayout.MODE_TEXT));
        findViewById(R.id.paint).setOnClickListener(v -> editImageLayout.start(EditImageLayout.MODE_PAINT));
        findViewById(R.id.back).setOnClickListener(v -> editImageLayout.start(EditImageLayout.MODE_BACK));
        findViewById(R.id.empty).setOnClickListener(v -> editImageLayout.start(EditImageLayout.MODE_EMPTY));
        findViewById(R.id.save).setOnClickListener(v -> editImageLayout.save());
        findViewById(R.id.update).setOnClickListener(v -> editImageLayout.update());

        editImageLayout
                .setText(getString(R.string.app_name))
                .setSavePath(savePath)
                .setEditImageListener(new EditImageLayout.EditImageListener() {
                    @Override
                    public void updateSuccess(@NonNull Bitmap bitmap) {
                        Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void updateError() {
                        Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void saveSuccess(@NonNull String path) {
                        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void saveError() {
                        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
