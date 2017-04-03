package com.numberpickerview.sample.numberpicker.city;

/**
 * by y on 2017/4/3.
 */

public interface EasyCityListener {
    void onEasyCancel();

    void onEasyNext(String provinceValue, String cityValue, String areaValue);
}
