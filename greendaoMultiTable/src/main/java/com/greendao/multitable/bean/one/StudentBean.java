package com.greendao.multitable.bean.one;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * by y on 05/07/2017.
 */
@Entity
public class StudentBean {

    @Id
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String age;


    @NotNull
    private String sex;


    @Generated(hash = 910889249)
    public StudentBean(long id, @NotNull String name, @NotNull String age,
            @NotNull String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


    @Generated(hash = 2097171990)
    public StudentBean() {
    }


    public long getId() {
        return this.id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAge() {
        return this.age;
    }


    public void setAge(String age) {
        this.age = age;
    }


    public String getSex() {
        return this.sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }

}
