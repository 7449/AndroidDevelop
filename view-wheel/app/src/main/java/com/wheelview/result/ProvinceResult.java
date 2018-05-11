package com.wheelview.result;

import java.util.List;

/**
 * by y on 2016/3/9
 */
public class ProvinceResult {
    private String name;
    private List<CityResult> cityList;

    public ProvinceResult() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityResult> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityResult> cityList) {
        this.cityList = cityList;
    }


}
