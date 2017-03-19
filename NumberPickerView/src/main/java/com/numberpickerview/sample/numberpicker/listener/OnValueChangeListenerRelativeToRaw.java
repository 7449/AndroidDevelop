package com.numberpickerview.sample.numberpicker.listener;

import com.numberpickerview.sample.numberpicker.NumberPickerView;

/**
 * by y on 2017/3/16
 */

public interface OnValueChangeListenerRelativeToRaw {
    void onValueChangeRelativeToRaw(NumberPickerView picker, int oldPickedIndex, int newPickedIndex, String[] displayedValues);
}
