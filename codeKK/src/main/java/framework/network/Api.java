package framework.network;

import com.codekk.projects.model.ProjectsModel;
import com.codekk.search.model.SearchModel;

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

    static final String BASE_API = "http://api.codekk.com/";

    //开源项目
    private static final String PROJECTS_URL = "op/page/";

    //搜索
    private static final String SEARCH_ULR = "op/search";

    public interface CodeKKService {
        @Headers(NetWork.CACHE_CONTROL_AGE + NetWork.CACHE_STALE_SHORT)
        @GET(Api.PROJECTS_URL + "{page}")
        Observable<BaseModel<ProjectsModel>> getProjects(@Path("page") int page, @Query("type") int type);

        @Headers(NetWork.CACHE_CONTROL_AGE + NetWork.CACHE_STALE_SHORT)
        @GET(Api.SEARCH_ULR)
        Observable<BaseModel<SearchModel>> getSearch(@Query("text") String text);
    }
}
