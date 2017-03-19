package com.numberpickerview.sample.numberpicker.radio;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.numberpickerview.sample.R;
import com.numberpickerview.sample.numberpicker.NumberPickerView;


/**
 * by y on 2017/3/16
 */

public class EasyPickerView
        extends DialogFragment
        implements View.OnClickListener {

    private String title;
    private boolean isCancelable;
    private EasyPickerListener listener;

    private String[] textArray;
    private String hintText;
    private int hintTextColor;
    private int divColor;
    private int selectColor;
    private int value;

    private View rootView;
    private NumberPickerView pickerView;
    private AlertDialog dialog;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow()
                    .setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BottomDialog);
        initView();
        builder.setView(rootView);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            window.setAttributes(wlp);
        }
        setCancelable(isCancelable);
        return dialog;
    }

    private void initView() {
        rootView = getActivity().getLayoutInflater().inflate(R.layout.picker_easy_view, null);
        AppCompatTextView tvTitle = (AppCompatTextView) rootView.findViewById(R.id.easy_title);
        pickerView = (NumberPickerView) rootView.findViewById(R.id.easy_picker);
        rootView.findViewById(R.id.easy_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.easy_next).setOnClickListener(this);
        tvTitle.setText(title);
        initPickerView();
    }

    private void initPickerView() {
        pickerView.setDisplayedValues(textArray);
        pickerView.setHintText(hintText);
        pickerView.setMaxValue(textArray.length - 1);
        pickerView.setMinValue(0);
        pickerView.setHintTextColor(hintTextColor);
        pickerView.setDividerColor(divColor);
        pickerView.setSelectedTextColor(selectColor);
        pickerView.setValue(value);
    }


    public void init(@NonNull Builder builder) {
        this.listener = builder.listener;
        this.title = builder.title;
        this.isCancelable = builder.isCancelable;
        this.textArray = builder.textArray;
        this.hintText = builder.hintText;
        this.hintTextColor = builder.hintTextColor;
        this.divColor = builder.dividerColor;
        this.selectColor = builder.selectTextColor;
        this.value = builder.value;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.easy_cancel) {
            listener.onEasyCancel();
        } else if (v.getId() == R.id.easy_next) {
            listener.onEasyNext(pickerView.getContentByCurrValue());
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static class Builder {
        private String title;
        private boolean isCancelable;
        private EasyPickerListener listener;
        private String[] textArray;
        private String hintText;
        private int hintTextColor;
        private int dividerColor;
        private int selectTextColor;
        private int value;

        public Builder(@NonNull EasyPickerListener listener) {
            this.listener = listener;
        }

        public Builder setValue(int value) {
            this.value = value;
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

        public Builder setHintTextColor(@ColorRes int hintTextColor) {
            this.hintTextColor = hintTextColor;
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

        public Builder setTextArray(@NonNull String[] textArray) {
            this.textArray = textArray;
            return this;
        }

        public Builder setHintText(@NonNull String hintText) {
            this.hintText = hintText;
            return this;
        }


        public EasyPickerView show(@NonNull FragmentManager fragmentManager, @NonNull String tag) {
            EasyPickerView easySelectPickerView =
                    (EasyPickerView) fragmentManager.findFragmentByTag(tag);
            if (easySelectPickerView == null) {
                easySelectPickerView = new EasyPickerView();
            }
            easySelectPickerView.init(this);
            easySelectPickerView.show(fragmentManager, tag);
            return easySelectPickerView;
        }
    }
}
