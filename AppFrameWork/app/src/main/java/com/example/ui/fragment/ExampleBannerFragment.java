package com.example.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.Interface.BannerModelCallBack;
import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannerlayout.widget.BannerLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleBannerFragment extends BaseFragment {

    @BindView(R.id.banner)
    BannerLayout banner;

    @Override
    protected void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        banner
                .setImageLoaderManager(new GlideImageLoaderManager())
                .setPageNumViewMargin(10)
                .initPageNumView()
                .initTips(true, true, false)
                .initListResources(bannerData())
                .switchBanner(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_banner_example;
    }


    @Override
    public void onDestroyView() {
        banner.clearBanner();
        super.onDestroyView();
    }

    private List<BannerModel> bannerData() {
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg"));
        mDatas.add(new BannerModel("http://pic.58pic.com/58pic/13/87/72/73t58PICjpT_1024.jpg"));
        mDatas.add(new BannerModel("http://img1.3lian.com/2015/a1/113/d/10.jpg"));
        mDatas.add(new BannerModel("http://pic39.nipic.com/20140325/9855626_214029182103_2.jpg"));
        mDatas.add(new BannerModel("http://pic15.nipic.com/20110803/7180732_211822337168_2.jpg"));
        mDatas.add(new BannerModel("http://img1.3lian.com/2015/a1/124/d/234.jpg"));
        return mDatas;
    }


    private static class GlideImageLoaderManager implements ImageLoaderManager<BannerModel> {
        private RequestOptions requestOptions;

        GlideImageLoaderManager() {
            requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .centerCrop();
        }

        @NonNull
        @Override
        public ImageView display(@NonNull ViewGroup container, BannerModel model) {
            ImageView imageView = new ImageView(container.getContext());
            Glide
                    .with(imageView.getContext())
                    .load(model.image)
                    .apply(requestOptions)
                    .into(imageView);
            return imageView;
        }
    }

    private static class BannerModel implements BannerModelCallBack<String> {
        Object image;

        BannerModel(Object image) {
            this.image = image;
        }

        @Override
        public String getBannerUrl() {
            return String.valueOf(image);
        }

        @Override
        public String getBannerTitle() {
            return null;
        }
    }
}
