package sample.view.develop.android.numberpicker.widget.listener;


import sample.view.develop.android.numberpicker.widget.NumberPickerView;

/**
 * by y on 2017/3/16
 */

public interface OnValueChangeListenerRelativeToRaw {
    void onValueChangeRelativeToRaw(NumberPickerView picker, int oldPickedIndex, int newPickedIndex, String[] displayedValues);
}
