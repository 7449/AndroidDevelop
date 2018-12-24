package com.lock.setting.model;

/**
 * by y on 2017/2/16
 */

public class SettingBean {
    public SettingBean() {
    }

    public SettingBean(String itemString) {
        this.itemString = itemString;
    }

    public String getItemString() {
        return itemString;
    }

    public void setItemString(String itemString) {
        this.itemString = itemString;
    }

    private String itemString;
}
