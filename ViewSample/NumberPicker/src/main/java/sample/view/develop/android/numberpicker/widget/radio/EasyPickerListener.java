package sample.view.develop.android.numberpicker.widget.radio;

/**
 * by y on 2017/3/16
 * <p>
 * EasyPickerDialogFragment点击回调
 * <p>
 * value :  NumberPickerView 选中item的值
 */

public interface EasyPickerListener {
    void onEasyCancel();

    void onEasyNext(String value);
}
