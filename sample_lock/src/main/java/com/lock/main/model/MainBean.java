package com.lock.main.model;

/**
 * by y on 2017/2/16
 */

public class MainBean {
    private String userName;
    private String passWord;

    public MainBean(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public MainBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
