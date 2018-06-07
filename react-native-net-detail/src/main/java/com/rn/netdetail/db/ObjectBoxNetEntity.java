package com.rn.netdetail.db;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ObjectBoxNetEntity {
    @Id
    public long id;
    public String url;
    public String headers;
    public String content;
    public String method;
    public String parameter;
}
