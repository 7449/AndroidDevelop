package com.compiler;

/**
 * by y.
 * <p>
 * Description:
 */
class BindEntity {

    public String name;
    public int id;
    public int type;
    public String clickMethodName;
    public String longClickMethodName;

    public BindEntity(String name, int id, int type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public BindEntity(String name, int id, int type, String clickMethodName, String longClickMethodName) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.clickMethodName = clickMethodName;
        this.longClickMethodName = longClickMethodName;
    }
}