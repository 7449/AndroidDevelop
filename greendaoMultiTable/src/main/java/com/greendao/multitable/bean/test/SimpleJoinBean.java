package com.greendao.multitable.bean.test;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * by y on 05/07/2017.
 */

@Entity
public class SimpleJoinBean {
    @Id
    private long id;
    private long schoolBeanId;
    private long classBeanId;
    @Generated(hash = 1218973811)
    public SimpleJoinBean(long id, long schoolBeanId, long classBeanId) {
        this.id = id;
        this.schoolBeanId = schoolBeanId;
        this.classBeanId = classBeanId;
    }
    @Generated(hash = 427993863)
    public SimpleJoinBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getSchoolBeanId() {
        return this.schoolBeanId;
    }
    public void setSchoolBeanId(long schoolBeanId) {
        this.schoolBeanId = schoolBeanId;
    }
    public long getClassBeanId() {
        return this.classBeanId;
    }
    public void setClassBeanId(long classBeanId) {
        this.classBeanId = classBeanId;
    }
}
