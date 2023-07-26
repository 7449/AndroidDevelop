package com.md.reader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;

import com.md.reader.markdown.MarkdownView;
import com.md.reader.markdown.css.styles.Github;

import java.io.File;

public class MainActivity extends Activity {

    private Github github = new Github();
    private MarkdownView mdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mdView = new MarkdownView(getApplicationContext());
        setContentView(mdView);
        load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdView != null) {
            mdView.destroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && mdView.canGoBack()) {
            mdView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void load() {
        if (!Intent.ACTION_DEFAULT.equals(getIntent().getAction())) {
            finish();
            return;
        }
        Uri data = getIntent().getData();
        if (data == null) {
            finish();
            return;
        }
        String realPathFromURI = getRealPathFromURI(data);
        if (TextUtils.isEmpty(realPathFromURI)) {
            finish();
            return;
        }
        File file = new File(realPathFromURI);
        if (!file.exists()) {
            finish();
            return;
        }
        setTitle(file.getName());
        mdView.addStyleSheet(github);
        WebSettings settings = mdView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        mdView.loadMarkdownFromFile(file);
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        String path = "";
        if (cursor != null && cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        }
        if (cursor != null) {
            cursor.close();
        }
        return path;
    }
}
