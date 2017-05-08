package com.numberpickerview.widget.listener;

import com.numberpickerview.widget.NumberPickerView;

/**
 * by y on 2017/3/16
 */

public interface OnValueChangeListenerRelativeToRaw {
    void onValueChangeRelativeToRaw(NumberPickerView picker, int oldPickedIndex, int newPickedIndex, String[] displayedValues);
}
