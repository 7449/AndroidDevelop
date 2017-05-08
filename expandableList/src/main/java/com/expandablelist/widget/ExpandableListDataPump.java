package com.expandablelist.widget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * by y on 2016/11/21
 */

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();
        List<String> technology = new ArrayList<>();
        technology.add("A");
        technology.add("B");
        technology.add("C");
        technology.add("D");
        technology.add("E");
        technology.add("F");
        technology.add("G");
        technology.add("H");
        technology.add("I");
        technology.add("J");
        List<String> entertainment = new ArrayList<>();
        entertainment.add("A");
        entertainment.add("B");
        entertainment.add("C");
        entertainment.add("D");
        entertainment.add("E");
        entertainment.add("F");
        entertainment.add("G");
        entertainment.add("H");
        entertainment.add("I");
        entertainment.add("J");
        List<String> science = new ArrayList<>();
        science.add("A");
        science.add("B");
        science.add("C");
        science.add("D");
        science.add("E");
        science.add("F");
        science.add("G");
        science.add("H");
        science.add("I");
        science.add("J");
        expandableListDetail.put("SIMPLE ONE", technology);
        expandableListDetail.put("SIMPLE TWO", entertainment);
        expandableListDetail.put("SIMPLE THREE", science);
        return expandableListDetail;
    }
}
