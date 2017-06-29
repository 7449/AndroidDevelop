package com.viewpagerfragment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * by y on 29/06/2017.
 */

public class NetApi {
    public static final String ZL_BASE_API = "https://zhuanlan.zhihu.com/api/";

    interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        Observable<List<Bean>> getList(@Path("suffix") String suffix,
                                       @Query("limit") int limit,
                                       @Query("offset") int offset);
    }

}
