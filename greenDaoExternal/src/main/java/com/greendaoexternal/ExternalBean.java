package com.greendaoexternal;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * by y on 2016/12/12
 */
@Entity(nameInDb = "blacklist")
public class ExternalBean {
    @Property(nameInDb = "id")
    private Integer id;
    @Property(nameInDb = "email")
    private String email;

    @Generated(hash = 1314000312)
    public ExternalBean(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    @Generated(hash = 981826822)
    public ExternalBean() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
