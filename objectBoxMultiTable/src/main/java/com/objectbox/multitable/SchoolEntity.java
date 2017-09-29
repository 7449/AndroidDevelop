package com.objectbox.multitable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * by y on 29/09/2017.
 */

@Entity
public class SchoolEntity {

    @Id
    long id;

    ToMany<StudentEntity> student;
}
