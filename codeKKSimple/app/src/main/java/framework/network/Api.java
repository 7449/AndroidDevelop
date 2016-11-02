package framework.network;

import com.codekk.p.projects.model.ProjectsModel;
import com.codekk.p.search.model.SearchModel;

import framework.base.BaseModel;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * by y on 2016/8/7.
 */
public class Api {

    public static final String BASE_API = "http://api.codekk.com/";

    //开源项目
    public static final String PROJECTS_URL = "op/page/";

    //搜索
    public static final String SEARCH_ULR = "op/search";

    public interface CodeKKService {
        @Headers(NetWork.CACHE_CONTROL_AGE + NetWork.CACHE_STALE_SHORT)
        @GET(Api.PROJECTS_URL + "{page}")
        Observable<BaseModel<ProjectsModel>> getProjects(@Path("page") int page, @Query("type") int type);

        @Headers(NetWork.CACHE_CONTROL_AGE + NetWork.CACHE_STALE_SHORT)
        @GET(Api.SEARCH_ULR)
        Observable<BaseModel<SearchModel>> getSearch(@Query("text") String text);
    }
}
