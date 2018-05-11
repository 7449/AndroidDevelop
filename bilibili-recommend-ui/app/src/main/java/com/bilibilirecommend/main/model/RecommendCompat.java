package com.bilibilirecommend.main.model;

/**
 * by y on 2017/2/18.
 */

public class RecommendCompat {

    public static final int TYPE_BANNER = -1;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_WEB_LINK = 3;
    public static final int TYPE_ACTIVITY = 4;
    public int position;
    public int newPosition;
    public int type;
    public int itemPosition;

    public RecommendCompat(int position, int type, int newPosition) {
        this.position = position;
        this.type = type;
        this.newPosition = newPosition;
    }

    public RecommendCompat(int position, int type, int newPosition, int itemPosition) {
        this.position = position;
        this.newPosition = newPosition;
        this.type = type;
        this.itemPosition = itemPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }
}
