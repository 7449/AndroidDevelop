package framework.network;

import com.readlist.news.model.NewsListModel;
import com.readlist.picture.model.PictureModel;
import com.readlist.weixin.model.WXHotModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * by y on 2016/8/7.
 */
public class Api {

    public static final String BAIDU_BASE = "http://apis.baidu.com/";

    private static final String TX = "txapi/";
    //微信精选
    private static final String WEIXIN = "weixin/wxhot";
    //美女图片
    private static final String PICTURE = "mvtp/meinv";

    public interface ApiService {
        @GET(TX + WEIXIN)
        Observable<WXHotModel> getWXHotList(@Query("num") int num,
                                            @Query("word") String word,
                                            @Query("page") int page);

        @GET(TX + PICTURE)
        Observable<PictureModel> getPictureList(@Query("num") int num,
                                                @Query("word") String word,
                                                @Query("page") int page);

        @GET(TX + "{suffix}")
        Observable<NewsListModel> getNewsList(@Path("suffix") String suffix,
                                              @Query("num") int num,
                                              @Query("page") int page);
    }
}
