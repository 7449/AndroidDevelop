package com.compiler;

/**
 * by y.
 * <p>
 * Description:
 */
class BindEntity {

    public static final int TYPE_VIEW = 0;
    public static final int TYPE_STRING = 1;
    public static final int TYPE_COLOR = 2;
    public static final int TYPE_DIMEN = 3;

    public String name;
    public int id;
    public int type;

    public BindEntity(String name, int id, int type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }
}