package com.rn.netdetail.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.rn.netdetail.db.ObjectBoxNetEntity;
import com.rn.netdetail.db.ObjectBoxUtils;

import java.io.IOException;

import io.objectbox.Box;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * by y on 01/08/2017.
 */
@Deprecated
public class LogInterceptor implements Interceptor {


    private Box<ObjectBoxNetEntity> listBox;

    public LogInterceptor() {
        listBox = ObjectBoxUtils.getListBox();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(chain.request());
        ObjectBoxNetEntity entity = new ObjectBoxNetEntity();
        if (response.networkResponse() != null) {
            entity.headers = response.networkResponse().request().headers().toString();
        }
        if (!TextUtils.equals("localhost", request.url().host())) {
            entity.url = request.url().toString();
            entity.method = request.method();
            entity.content = response.peekBody(1024 * 1024).string();
            listBox.put(entity);
        }
        return response;
    }
}
