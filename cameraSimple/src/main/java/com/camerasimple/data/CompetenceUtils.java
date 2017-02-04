package com.camerasimple.data;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;

/**
 * by y on 2016/5/4.
 */
public class CompetenceUtils {

    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static void getPermissions(final Context context, final String permissions, final CompetenceCompat competenceCompat) {
        RxPermissions.getInstance(context)
                .request(permissions)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "请求权限出现异常", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Boolean isPermissions) {
                        Log.i("onNext", isPermissions + "");
                        if (isPermissions) {
                            competenceCompat.onPermissionsSuccess();
                        } else {
                            competenceCompat.onPermissionsError();
                        }
                    }
                });
    }

    public interface CompetenceCompat {
        void onPermissionsSuccess();

        void onPermissionsError();
    }

}
