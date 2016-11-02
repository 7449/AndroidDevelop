package framework.network;


import com.codekk.p.projects.model.ProjectsModel;
import com.codekk.p.search.model.SearchModel;

import framework.utils.RxUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/8/7.
 */
public class NetWorkRequest {

    public static void getProjects(int page, int type, Subscriber<ProjectsModel> subscriber) {
        RxUtils.unsubscribe();
        RxUtils.subscription = NetWork
                .getCodeKK()
                .getProjects(page, type)
                .map(new NetWork.NetWorkResultFunc<ProjectsModel>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getSearch(String text, Subscriber<SearchModel> subscriber) {
        RxUtils.unsubscribe();
        RxUtils.subscription = NetWork
                .getCodeKK()
                .getSearch(text)
                .map(new NetWork.NetWorkResultFunc<SearchModel>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}

