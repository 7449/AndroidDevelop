package com.objectbox;

import android.support.annotation.NonNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

/**
 * by y on 28/09/2017.
 */
@Entity
@NameInDb("SampleObjectBoxEntity")
public class ObjectBoxEntity {

    @Id
    private long id;
    @NonNull
    private String name;
    private int age;

    public ObjectBoxEntity(long id, @NonNull String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public ObjectBoxEntity(@NonNull String name, int age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
