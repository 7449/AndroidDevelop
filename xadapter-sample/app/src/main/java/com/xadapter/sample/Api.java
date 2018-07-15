package com.xadapter.sample;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * by y.
 * <p>
 * Description:
 */
public class Api {

    public static final String BASE_URL = "https://zhuanlan.zhihu.com/api/";

    interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        Observable<List<Entity>> getList(@Path("suffix") String suffix, @Query("limit") int limit, @Query("offset") int offset);
    }

}
