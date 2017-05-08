package com.banner.util;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * by y on 2016/9/18.
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

    public List<T> mDatas = null;

    public BasePagerAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        //数字无限大，如果用户真的能滑到最后一张 面对这样的人  认怂，怼不过
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= mDatas.size();
        ImageView img = new ImageView(container.getContext());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //这里是本地数据，如果是网络数据请使用Glide或者其他的加载工具实现
        // Glide.with(context).load(url).placeholder(R.drawable.bili_default_image)
        // .error(R.drawable.bili_default_image).centerCrop().into(imageView);
        //用Glide举例，一般url是String类型,把displayImage改成String即可

        img.setBackgroundResource(displayImage(position));

        final int finalPosition = position;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(finalPosition, mDatas.get(finalPosition));
            }
        });
        container.addView(img);
        return img;
    }

    protected abstract int displayImage(int position);

    protected abstract void onImageClick(int finalPosition, T mDatas);

}
