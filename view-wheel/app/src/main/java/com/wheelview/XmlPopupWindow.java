package com.wheelview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wheelview.interfaces.XmlManagerInterface;
import com.wheelview.interfaces.XmlPopupWindowInterface;
import com.wheelview.widget.WheelView;
import com.wheelview.widget.adapter.ArrayWheelAdapter;
import com.wheelview.widget.interfaces.OnWheelChangedListener;


/**
 * by y on 2016/10/26
 */

public class XmlPopupWindow extends PopupWindow
        implements OnWheelChangedListener, XmlManagerInterface, View.OnClickListener {
    private Context context;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private XmlManager xmlManager;
    private View rootView;
    private XmlPopupWindowInterface xmlPopupWindowInterface = null;

    public XmlPopupWindow(View contentView, final XmlPopupWindowInterface xmlPopupWindowInterface) {
        super(contentView);
        this.xmlPopupWindowInterface = xmlPopupWindowInterface;
        xmlManager = new XmlManager(this);
        initPopupWindow();
        initWheelView();
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    private void initPopupWindow() {
        setContentView(initView());
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.anim_menu_wheel_view);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0xb0000000));
    }

    private View initView() {
        rootView = getContentView();
        context = rootView.getContext();
        mViewProvince = (WheelView) rootView.findViewById(R.id.id_province);
        mViewCity = (WheelView) rootView.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) rootView.findViewById(R.id.id_district);
        rootView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        rootView.findViewById(R.id.btn_dismiss).setOnClickListener(this);
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        return rootView;
    }

    private void initWheelView() {
        xmlManager.initProvinceDatas(context.getAssets());
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(context, xmlManager.getProvinceDatas()));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        xmlManager.updateCities(mViewProvince.getCurrentItem());
        xmlManager.updateAreas(mViewCity.getCurrentItem());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                if (xmlPopupWindowInterface != null) {
                    xmlPopupWindowInterface.setData(xmlManager.getCurrentProviceName(), xmlManager.getCurrentCityName(), xmlManager.getCurrentDistrictName());
                }
                dismiss();
                break;
            case R.id.btn_dismiss:
                dismiss();
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            xmlManager.updateCities(mViewProvince.getCurrentItem());
        } else if (wheel == mViewCity) {
            xmlManager.updateAreas(mViewCity.getCurrentItem());
        } else if (wheel == mViewDistrict) {
            xmlManager.update(newValue);
        }
    }

    @Override
    public void updateAreas(String[] areas) {
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(context, areas));
        mViewDistrict.setCurrentItem(0);
    }

    @Override
    public void updateCities(String[] cities) {
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(context, cities));
        mViewCity.setCurrentItem(0);
        xmlManager.updateAreas(mViewCity.getCurrentItem());
    }

}
