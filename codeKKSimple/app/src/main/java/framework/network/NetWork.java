package framework.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import framework.App;
import framework.base.BaseModel;
import framework.utils.UIUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Func1;

/**
 * by y on 2016/8/7.
 */
public class NetWork {

    public static final int CACHE_STALE_SHORT = 60;
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";


    private static Api.CodeKKService codeKKService;
    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .cache(new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 100))
                .addNetworkInterceptor(mCacheInterceptor)
                .addInterceptor(mCacheInterceptor)
                .addInterceptor(mLogInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static Api.CodeKKService getCodeKK() {
        if (codeKKService == null) {
            codeKKService = getRetrofit().create(Api.CodeKKService.class);
        }
        return codeKKService;
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(getOkHttp())
                .baseUrl(Api.BASE_API)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }


    public static class NetWorkResultFunc<T> implements Func1<BaseModel<T>, T> {

        @Override
        public T call(BaseModel<T> model) {
            if (model.getCode() != 0) {
                throw new NetWorkException(model.getCode(), model.getMessage());
            }
            Log.i(UIUtils.getSimpleName(), model.getCode() + "---" + model.getMessage());
            return model.getData();
        }
    }

    private static class NetWorkException extends RuntimeException {

        public NetWorkException(int code, String message) {
            UIUtils.Toast(code + "---" + message);
        }

    }

    private static Interceptor mCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (isNetworkConnected()) {
                Log.i(UIUtils.getSimpleName(), chain.request().toString());
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                Log.i(UIUtils.getSimpleName(), chain.request().toString());
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };

    private static boolean isNetworkConnected() {
        if (App.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    private static Interceptor mLogInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i(UIUtils.getSimpleName(), content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    };
}
