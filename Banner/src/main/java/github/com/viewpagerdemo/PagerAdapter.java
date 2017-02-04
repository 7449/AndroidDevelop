package github.com.viewpagerdemo;

import java.util.List;

import github.com.viewpagerdemo.util.BasePagerAdapter;

/**
 * by y on 2016/9/28
 */

public class PagerAdapter extends BasePagerAdapter<PagerModel> {

    public PagerAdapter(List<PagerModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int displayImage(int position) {
        return mDatas.get(position).getImageId();
    }

    @Override
    protected void onImageClick(int finalPosition, PagerModel mDatas) {

    }
}
