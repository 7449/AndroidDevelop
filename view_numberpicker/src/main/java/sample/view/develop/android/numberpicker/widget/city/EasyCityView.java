package sample.view.develop.android.numberpicker.widget.city;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import sample.view.develop.android.numberpicker.R;
import sample.view.develop.android.numberpicker.widget.NumberPickerView;
import sample.view.develop.android.numberpicker.widget.city.json.JsonDataUtils;
import sample.view.develop.android.numberpicker.widget.listener.OnValueChangeListener;


/**
 * by y on 2017/3/16
 */

public class EasyCityView
        extends DialogFragment
        implements View.OnClickListener {

    private String title;
    private boolean isCancelable;
    private EasyCityListener listener;

    private int divColor;
    private int selectColor;
    private String provinceName;  // 默认选中省
    private String cityName;  //默认选中市
    private String areaName; //默认选中区

    private View rootView;
    private NumberPickerView provinceView;
    private NumberPickerView cityView;
    private NumberPickerView areaView;
    private AlertDialog dialog;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes == null) {
            return;
        }
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BottomDialog);
        initView();
        builder.setView(rootView);
        dialog = builder.create();
        setCancelable(isCancelable);
        return dialog;
    }

    private void initView() {
        JsonDataUtils.get().init(getContext());//初始化数据，必须先执行这一步
        rootView = getActivity().getLayoutInflater().inflate(R.layout.city_easy_view, null);
        AppCompatTextView tvTitle = (AppCompatTextView) rootView.findViewById(R.id.easyTitle);
        provinceView = (NumberPickerView) rootView.findViewById(R.id.easyProvince);
        cityView = (NumberPickerView) rootView.findViewById(R.id.easyCity);
        areaView = (NumberPickerView) rootView.findViewById(R.id.easyArea);
        rootView.findViewById(R.id.easyCancel).setOnClickListener(this);
        rootView.findViewById(R.id.easyNext).setOnClickListener(this);
        tvTitle.setText(title);
        initViewData();
    }

    private void initViewData() {
        String[] provinceDatas = JsonDataUtils.get().getProvinceDatas();
        if (TextUtils.isEmpty(provinceName)) {
            provinceName = "北京市";
        }
        if (TextUtils.isEmpty(cityName)) {
            cityName = "北京市";
        }
        if (TextUtils.isEmpty(areaName)) {
            areaName = "东城区";
        }
        JsonDataUtils.get().initValue(provinceName, cityName, areaName);
        provinceView.setDisplayedValues(provinceDatas);
        provinceView.setMaxValue(provinceDatas.length - 1);
        provinceView.setMinValue(0);
        provinceView.setDividerColor(divColor);
        provinceView.setSelectedTextColor(selectColor);
        provinceView.setValue(JsonDataUtils.get().getProvinceValue());

        provinceView.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                cityView.refreshByNewDisplayedValues(JsonDataUtils.get().getCity(provinceView.getContentByCurrValue()));
                areaView.refreshByNewDisplayedValues(JsonDataUtils.get().getArea(cityView.getContentByCurrValue()));
            }
        });


        String[] city = JsonDataUtils.get().getCity(provinceName);
        cityView.setDisplayedValues(city);
        cityView.setMaxValue(city.length - 1);
        cityView.setMinValue(0);
        cityView.setDividerColor(divColor);
        cityView.setSelectedTextColor(selectColor);
        cityView.setValue(JsonDataUtils.get().getCityValue());


        cityView.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                areaView.refreshByNewDisplayedValues(JsonDataUtils.get().getArea(cityView.getContentByCurrValue()));
            }
        });


        String[] area = JsonDataUtils.get().getArea(cityName);
        areaView.setDisplayedValues(area);
        areaView.setMaxValue(area.length - 1);
        areaView.setMinValue(0);
        areaView.setDividerColor(divColor);
        areaView.setSelectedTextColor(selectColor);
        areaView.setValue(JsonDataUtils.get().getAreaValue());
    }


    public void init(@NonNull Builder builder) {
        this.listener = builder.listener;
        this.title = builder.title;
        this.isCancelable = builder.isCancelable;
        this.divColor = builder.dividerColor;
        this.selectColor = builder.selectTextColor;
        this.provinceName = builder.provinceName;
        this.cityName = builder.cityName;
        this.areaName = builder.areaName;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.easyCancel) {
            listener.onEasyCancel();
        } else if (v.getId() == R.id.easyNext) {
            listener.onEasyNext(provinceView.getContentByCurrValue(), cityView.getContentByCurrValue(), areaView.getContentByCurrValue());
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static class Builder {
        private String title;
        private boolean isCancelable;
        private EasyCityListener listener;
        private int dividerColor;
        private int selectTextColor;

        private String provinceName;
        private String cityName;
        private String areaName;

        public Builder(@NonNull EasyCityListener listener) {
            this.listener = listener;
        }

        public Builder setProvinceName(String provinceName) {
            this.provinceName = provinceName;
            return this;
        }

        public Builder setCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder setAreaName(String areaName) {
            this.areaName = areaName;
            return this;
        }

        public Builder setSelectTextColor(@ColorRes int selectTextColor) {
            this.selectTextColor = selectTextColor;
            return this;
        }

        public Builder setDividerColor(@ColorRes int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public EasyCityView show(@NonNull FragmentManager fragmentManager, @NonNull String tag) {
            EasyCityView easyCityView =
                    (EasyCityView) fragmentManager.findFragmentByTag(tag);
            if (easyCityView == null) {
                easyCityView = new EasyCityView();
            }
            easyCityView.init(this);
            easyCityView.show(fragmentManager, tag);
            return easyCityView;
        }
    }
}
