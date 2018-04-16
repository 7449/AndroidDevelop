package com.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.album.AlbumListener;
import com.album.model.AlbumModel;
import com.album.ui.annotation.PermissionsType;

import java.io.File;
import java.util.List;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleAlbumListener implements AlbumListener {

    private Context context;

    private void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    protected ExampleAlbumListener(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void onAlbumActivityFinish() {
        toast("album activity finish");
    }

    @Override
    public void onAlbumPermissionsDenied(@PermissionsType int type) {
        toast("permissions error");
    }

    @Override
    public void onAlbumFragmentNull() {
        toast("album fragment null");
    }

    @Override
    public void onAlbumPreviewFileNull() {
        toast("preview image has been deleted");
    }

    @Override
    public void onAlbumFinderNull() {
        toast("folder directory is empty");
    }

    @Override
    public void onAlbumBottomPreviewNull() {
        toast("preview no image");
    }

    @Override
    public void onAlbumBottomSelectNull() {
        toast("select no image");
    }

    @Override
    public void onAlbumFragmentFileNull() {
        toast("album image has been deleted");
    }

    @Override
    public void onAlbumPreviewSelectNull() {
        toast("PreviewActivity,  preview no image");
    }

    @Override
    public void onAlbumCheckBoxFileNull() {
        toast("check box  image has been deleted");
    }

    @Override
    public void onAlbumFragmentCropCanceled() {
        toast("cancel crop");
    }

    @Override
    public void onAlbumFragmentCameraCanceled() {
        toast("cancel camera");
    }

    @Override
    public void onAlbumFragmentUCropError(@Nullable Throwable data) {
        toast("crop error:" + data);
    }

    @Override
    public void onAlbumResources(@NonNull List<AlbumModel> list) {
        toast("select count :" + list.size());
    }

    @Override
    public void onAlbumUCropResources(@Nullable File scannerFile) {
        toast("crop file:" + scannerFile);
    }

    @Override
    public void onAlbumMaxCount() {
        toast("select max count");
    }

    @Override
    public void onAlbumActivityBackPressed() {
        toast("AlbumActivity Back");
    }

    @Override
    public void onAlbumOpenCameraError() {
        toast("camera error");
    }

    @Override
    public void onAlbumEmpty() {
        toast("no image");
    }

    @Override
    public void onAlbumNoMore() {
        toast("album no more");
    }

    @Override
    public void onAlbumResultCameraError() {
        toast("result error");
    }

    @Override
    public void onVideoPlayError() {
        toast("play video error : checked video app");
    }
}