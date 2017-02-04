package framework.network;

import android.util.Log;

import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * by y on 2016/8/7.
 */
public class NetWork {
    private static Api.ApiService apiService;
    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static Api.ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(Api.ApiService.class);
        }
        return apiService;
    }

    private static OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .addInterceptor(mHeadersInterceptor)
                .addInterceptor(mLogInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(getOkHttp())
                .baseUrl(Api.BAIDU_BASE)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    private static Interceptor mHeadersInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain
                    .request()
                    .newBuilder()
                    .addHeader("apikey", "139ac5b32ede0368d654a01505b30e15")
                    .build());
        }
    };

    private static Interceptor mLogInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            String content = response.body().string();
            KLog.i("LogUtils--> ", "request:" + chain.request().toString());
            Log.i("LogUtils--> ", "response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(response.body().contentType(), content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    };
}
