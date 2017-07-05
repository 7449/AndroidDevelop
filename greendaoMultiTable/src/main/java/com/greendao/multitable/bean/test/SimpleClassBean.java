package com.greendao.multitable.bean.test;

/**
 * by y on 05/07/2017.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SimpleClassBean {
    @Id
    private long id;

    @Generated(hash = 1233575941)
    public SimpleClassBean(long id) {
        this.id = id;
    }

    @Generated(hash = 1837338355)
    public SimpleClassBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}