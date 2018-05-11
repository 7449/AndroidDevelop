package com.example;

import com.example.entity.ExampleNetEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * by y.
 *
 * Description:
 */

public class Api {
    static final String ZL_BASE_API = "https://zhuanlan.zhihu.com/api/";

    public interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        Observable<List<ExampleNetEntity>> getList(@Path("suffix") String suffix, @Query("limit") int limit, @Query("offset") int offset);
    }
}
