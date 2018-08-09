package com.status.layout;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author y
 */
@StringDef({
        Status.NORMAL,
        Status.LOADING,
        Status.EMPTY,
        Status.SUCCESS,
        Status.ERROR
})
@Retention(RetentionPolicy.SOURCE)
public @interface Status {
    String NORMAL = "StatusLayout:Normal";
    String LOADING = "StatusLayout:Loading";
    String EMPTY = "StatusLayout:Empty";
    String SUCCESS = "StatusLayout:Success";
    String ERROR = "StatusLayout:Error";
}