package com.xadapter.sample

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * by y.
 *
 *
 * Description:
 */
object Api {

    const val BASE_URL = "https://zhuanlan.zhihu.com/api/"

    internal interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        fun getList(@Path("suffix") suffix: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Observable<List<Entity>>
    }

}
