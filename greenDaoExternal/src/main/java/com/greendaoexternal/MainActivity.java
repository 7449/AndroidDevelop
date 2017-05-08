package com.greendaoexternal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * greenDaoExternal:用greenDao读取本地数据库里面的表
 * <p>
 * android数据库操作都是在databases文件夹下，所以要把需要动的数据库复制到databases文件夹下
 * <p>
 * 需要注意的是greenDao自动生成的时候
 * table 默认为TABLENAME
 * 默认id生成时"_id"
 * 这些生成的字段必须要和数据表中的字段一致，可以用 nameInDb="" 指定字段name和tableName
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/com.greendaoexternal/databases/";
    private static final String DB_NAME = "external.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        copyDBToDatabases();
        recyclerView.setAdapter(
                new MainAdapter(
                        new DaoMaster(
                                new DaoMaster
                                        .DevOpenHelper(this, DB_NAME, null)
                                        .getWritableDatabase())
                                .newSession()
                                .getExternalBeanDao()
                                .loadAll()));
    }

    public void copyDBToDatabases() {
        try {
            String outFileName = DB_PATH + DB_NAME;
            File file = new File(DB_PATH);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            File dataFile = new File(outFileName);
            if (dataFile.exists()) {
                dataFile.delete();
            }
            InputStream myInput;
            myInput = getApplicationContext().getAssets().open(DB_NAME);
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            Log.i(TAG, "error--->" + e.toString());
            e.printStackTrace();
        }

    }
}
