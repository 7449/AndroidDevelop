package com.wheelview.result;

import java.util.List;

/**
 * by y on 2016/3/9
 */
public class CityResult {
    private String name;
    private List<DistrictResult> districtList;

    public CityResult() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictResult> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictResult> districtList) {
        this.districtList = districtList;
    }

}
