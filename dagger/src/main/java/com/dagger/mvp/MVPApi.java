package com.dagger.mvp;


import com.dagger.mvp.model.MVPBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * by y on 2017/5/31.
 */

public class MVPApi {
    public static final String ZL_BASE_API = "https://zhuanlan.zhihu.com/api/";

    public interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        Observable<List<MVPBean>> getList(@Path("suffix") String suffix, @Query("limit") int limit, @Query("offset") int offset);
    }
}
