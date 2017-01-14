package com.bilirecommendui.main.presenter;


import android.annotation.SuppressLint;

import com.bilirecommendui.R;
import com.bilirecommendui.main.model.RecommendBannerModel;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.main.view.RecommendView;
import com.bilirecommendui.network.NetWorkRequest;

import java.util.HashMap;

import rx.Subscriber;

import static com.bilirecommendui.network.Constant.TYPE_ACTIVITY;
import static com.bilirecommendui.network.Constant.TYPE_ANIMATION;
import static com.bilirecommendui.network.Constant.TYPE_CURRENT_FOCUS;
import static com.bilirecommendui.network.Constant.TYPE_DANCING;
import static com.bilirecommendui.network.Constant.TYPE_ENTERTAINMENT;
import static com.bilirecommendui.network.Constant.TYPE_FASHION;
import static com.bilirecommendui.network.Constant.TYPE_FOOTER_;
import static com.bilirecommendui.network.Constant.TYPE_GAMES;
import static com.bilirecommendui.network.Constant.TYPE_HEADER_;
import static com.bilirecommendui.network.Constant.TYPE_HOT_AIR;
import static com.bilirecommendui.network.Constant.TYPE_KICHIKU;
import static com.bilirecommendui.network.Constant.TYPE_LIFT;
import static com.bilirecommendui.network.Constant.TYPE_MOVIE;
import static com.bilirecommendui.network.Constant.TYPE_MUSIC;
import static com.bilirecommendui.network.Constant.TYPE_RECOMMEND_IMAGE;
import static com.bilirecommendui.network.Constant.TYPE_TECHNOLOGY;
import static com.bilirecommendui.network.Constant.TYPE_TV;

/**
 * by y on 2016/9/17.
 */
public class RecommendPresenterImpl implements RecommendPresenter {

    private int temp = 0;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, RecommendModel.RecommendAdapterTagModel> type = new HashMap<>();
    private RecommendView view;

    public RecommendPresenterImpl(RecommendView view) {
        this.view = view;
    }


    @Override
    public void netWorkRequest(int plat) {
        view.showProgress();
        NetWorkRequest.getRecommend(plat, new Subscriber<Object>() {
            @Override
            public void onStart() {
                super.onStart();
                view.showProgress();
            }

            @Override
            public void onCompleted() {
                view.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                view.hideProgress();
                view.netWorkError();
            }

            @Override
            public void onNext(Object o) {
                if (o instanceof RecommendBannerModel) {
                    RecommendBannerModel bannerModel = (RecommendBannerModel) o;
                    view.setBannerData(bannerModel.getData());
                }
                if (o instanceof RecommendModel) {
                    RecommendModel recommendModel = (RecommendModel) o;
                    initModel(recommendModel);
                }
            }
        });
    }

    /**
     * 推荐布局比较复杂 需要记下每个position的位置
     */
    private void initModel(RecommendModel recommendModel) {
        for (int i = 0; i < recommendModel.getResult().size(); i++) {
            switch (recommendModel.getResult().get(i).getHead().getTitle()) {
                case TYPE_CURRENT_FOCUS:
                    startModel(recommendModel, i, R.drawable.ic_header_hot);
                    break;
                case TYPE_HOT_AIR:
                    startModel(recommendModel, i, R.drawable.ic_head_live);
                    break;
                case TYPE_RECOMMEND_IMAGE:
                    startModel(recommendModel, i, R.drawable.ic_category_t13);
                    break;
                case TYPE_ANIMATION:
                    startModel(recommendModel, i, R.drawable.ic_category_t1);
                    break;
                case TYPE_MUSIC:
                    startModel(recommendModel, i, R.drawable.ic_category_t3);
                    break;
                case TYPE_DANCING:
                    startModel(recommendModel, i, R.drawable.ic_category_t129);
                    break;
                case TYPE_GAMES:
                    startModel(recommendModel, i, R.drawable.ic_category_t4);
                    break;
                case TYPE_KICHIKU:
                    startModel(recommendModel, i, R.drawable.ic_category_t119);
                    break;
                case TYPE_TECHNOLOGY:
                    startModel(recommendModel, i, R.drawable.ic_category_t36);
                    break;
                case TYPE_LIFT:
                    startModel(recommendModel, i, R.drawable.ic_category_t160);
                    break;
                case TYPE_FASHION:
                    startModel(recommendModel, i, R.drawable.ic_category_t155);
                    break;
                case TYPE_ENTERTAINMENT:
                    startModel(recommendModel, i, R.drawable.ic_category_t5);
                    break;
                case TYPE_TV:
                    startModel(recommendModel, i, R.drawable.ic_category_t11);
                    break;
                case TYPE_MOVIE:
                    startModel(recommendModel, i, R.drawable.ic_category_t23);
                    break;
                case TYPE_ACTIVITY:
                    initHeaderModel(i, R.drawable.ic_header_activity_center);
                    temp++;
                    type.put(temp, new RecommendModel.RecommendAdapterTagModel(i, recommendModel.getResult().get(i).getType(), -1, -1));
                    break;
                default:
                    initHeaderModel(i, R.drawable.ic_header_topic);
                    initItemModel(recommendModel, i);
                    break;
            }
        }
        recommendModel.setItemPositionCount(temp);
        recommendModel.setMaps(type);
        view.removeAdapter();
        view.setRecommendData(recommendModel);
        temp = 0;
    }

    private void startModel(RecommendModel recommendModel, int i, int ic_header_hot) {
        initHeaderModel(i, ic_header_hot);
        initItemModel(recommendModel, i);
        initFooterModel(i);
    }

    private void initHeaderModel(int i, int imagePage) {
        temp++;
        type.put(temp, new RecommendModel.RecommendAdapterTagModel(i, TYPE_HEADER_, -1, imagePage));
    }

    private void initFooterModel(int i) {
        temp++;
        type.put(temp, new RecommendModel.RecommendAdapterTagModel(i, TYPE_FOOTER_, -1, -1));
    }

    private void initItemModel(RecommendModel recommendModel, int i) {
        for (int j = 0; j < recommendModel.getResult().get(i).getBody().size(); j++) {
            temp++;
            type.put(temp, new RecommendModel.RecommendAdapterTagModel(i, recommendModel.getResult().get(i).getType(), j, -1));
        }
    }
}
