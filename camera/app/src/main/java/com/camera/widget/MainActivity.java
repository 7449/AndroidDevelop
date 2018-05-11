package com.camera.widget;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.camera.R;
import com.camera.data.CameraCallback;
import com.camera.data.CompetenceUtils;
import com.camera.data.StatusBarUtil;
import com.google.android.cameraview.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.camera.data.Constant.FLASH_ICONS;
import static com.camera.data.Constant.FLASH_OPTIONS;
import static com.camera.data.Constant.FLASH_TITLES;


public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener,
        CameraCallback.CallBackInterface, CompetenceUtils.CompetenceCompat {

    private int mCurrentFlash;
    private CameraView mCameraView;
    private Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = (CameraView) findViewById(R.id.camera);
        findViewById(R.id.take_picture).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        StatusBarUtil.setTranslucentForImageView(this, mCameraView);
        mCameraView.addCallback(new CameraCallback(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CompetenceUtils.getPermissions(this, CompetenceUtils.CAMERA, this);
    }

    @Override
    protected void onDestroy() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_flash:
                if (mCameraView != null) {
                    mCurrentFlash = (mCurrentFlash + 1) % FLASH_OPTIONS.length;
                    item.setTitle(FLASH_TITLES[mCurrentFlash]);
                    item.setIcon(FLASH_ICONS[mCurrentFlash]);
                    mCameraView.setFlash(FLASH_OPTIONS[mCurrentFlash]);
                }
                break;
            case R.id.switch_camera:
                if (mCameraView != null) {
                    int facing = mCameraView.getFacing();
                    mCameraView.setFacing(facing == CameraView.FACING_FRONT ?
                            CameraView.FACING_BACK : CameraView.FACING_FRONT);
                }
                break;
        }
        return false;
    }


    @Override
    public void onCameraClosed(CameraView cameraView) {
    }

    @Override
    public void onCameraOpened(CameraView cameraView) {
    }

    @Override
    public void onPictureTaken(CameraView cameraView, final byte[] data) {
        subscribe = Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        try {
                            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "picture.jpg");
                            OutputStream os = new FileOutputStream(file);
                            os.write(data);
                            subscriber.onNext(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath());
                            subscriber.onCompleted();
                            os.flush();
                            os.close();
                        } catch (IOException ignored) {
                            subscriber.onError(ignored);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(getClass().getSimpleName(), e.toString());
                        Toast.makeText(getApplicationContext(), "保存失败——> " + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getApplicationContext(), "保存路径为：-->  " + s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_picture:
                if (mCameraView != null) {
                    mCameraView.takePicture();
                }
                break;
        }
    }


    @Override
    public void onPermissionsSuccess() {
        mCameraView.start();
    }

    @Override
    public void onPermissionsError() {
        Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
        finish();
    }
}
