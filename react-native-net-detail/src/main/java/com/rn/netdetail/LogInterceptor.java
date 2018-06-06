package com.rn.netdetail;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import io.objectbox.Box;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * by y on 01/08/2017.
 */

public class LogInterceptor implements Interceptor {


    private Box<ObjectBoxNetEntity> listBox;

    LogInterceptor() {
        listBox = ObjectBoxUtils.getListBox();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(chain.request());
        ResponseBody body = response.body();
        if (body == null) {
            return response;
        }
        ObjectBoxNetEntity entity = new ObjectBoxNetEntity();
        okhttp3.MediaType mediaType = body.contentType();
        String content = body.string();
        Log.i("LogInterceptor", " url : " + request.url() + "  method:" + request.method());
        Response logResponse = response.networkResponse();
        if (logResponse != null) {
            entity.headers = logResponse.request().headers().toString();
            Log.i("LogInterceptor", " url : " + request.url() + "\n" + logResponse.request().headers());
        }
        Log.i("LogInterceptor", " url : " + request.url() + "   " + content);
        if (!TextUtils.equals("localhost", request.url().host())) {
            entity.url = request.url().toString();
            entity.method = request.method();
            entity.content = content;
            listBox.put(entity);
        }
        return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
    }
}
