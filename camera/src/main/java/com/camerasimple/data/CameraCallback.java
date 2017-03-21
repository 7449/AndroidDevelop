package com.camerasimple.data;

import android.util.Log;

import com.google.android.cameraview.CameraView;

/**
 * by y on 2016/11/21
 */

public class CameraCallback extends CameraView.Callback {

    private CallBackInterface callBackInterface = null;

    public CameraCallback(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }


    @Override
    public void onCameraClosed(CameraView cameraView) {
        Log.d(getClass().getSimpleName(), "onCameraClosed");
        if (callBackInterface != null) {
            callBackInterface.onCameraClosed(cameraView);
        }
        super.onCameraClosed(cameraView);
    }

    @Override
    public void onCameraOpened(CameraView cameraView) {
        Log.d(getClass().getSimpleName(), "onCameraOpened");
        if (callBackInterface != null) {
            callBackInterface.onCameraOpened(cameraView);
        }
        super.onCameraOpened(cameraView);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, byte[] data) {
        Log.d(getClass().getSimpleName(), "onPictureTaken " + data.length);
        if (callBackInterface != null) {
            callBackInterface.onPictureTaken(cameraView, data);
        }
        super.onPictureTaken(cameraView, data);
    }


    public interface CallBackInterface {
        void onCameraClosed(CameraView cameraView);

        void onCameraOpened(CameraView cameraView);

        void onPictureTaken(CameraView cameraView, byte[] data);
    }
}
