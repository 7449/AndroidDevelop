package com.example.y.demo.result;

/**
 * by y on 2016/3/9
 */
public class DistrictResult {
    private String name;
    private String zipcode;

    public DistrictResult() {
        super();
    }

    public DistrictResult(String name, String zipcode) {
        super();
        this.name = name;
        this.zipcode = zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


}
