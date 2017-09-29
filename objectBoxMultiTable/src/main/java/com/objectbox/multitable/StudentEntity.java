package com.objectbox.multitable;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * by y on 29/09/2017.
 */
@Entity
public class StudentEntity {

    @Id
    long id;
    @Backlink
    String name;
}
