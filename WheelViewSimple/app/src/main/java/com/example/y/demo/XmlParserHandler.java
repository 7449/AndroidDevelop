package com.example.y.demo;

import com.example.y.demo.result.CityResult;
import com.example.y.demo.result.DistrictResult;
import com.example.y.demo.result.ProvinceResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/3/9
 */
public class XmlParserHandler extends DefaultHandler {
    private static final String DISTRICT = "district";
    private static final String CITY = "city";
    private static final String PROVINCE = "province";
    private ProvinceResult provinceResult = new ProvinceResult();
    private CityResult cityResult = new CityResult();
    private DistrictResult districtResult = new DistrictResult();

    /**
     * 存储所有的解析对象
     */
    private List<ProvinceResult> provinceList = new ArrayList<>();

    public List<ProvinceResult> getDataList() {
        return provinceList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case PROVINCE:
                provinceResult = new ProvinceResult();
                provinceResult.setName(attributes.getValue(0));
                provinceResult.setCityList(new ArrayList<CityResult>());
                break;
            case CITY:
                cityResult = new CityResult();
                cityResult.setName(attributes.getValue(0));
                cityResult.setDistrictList(new ArrayList<DistrictResult>());
                break;
            case DISTRICT:
                districtResult = new DistrictResult();
                districtResult.setName(attributes.getValue(0));
                districtResult.setZipcode(attributes.getValue(1));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case DISTRICT:
                cityResult.getDistrictList().add(districtResult);
                break;
            case CITY:
                provinceResult.getCityList().add(cityResult);
                break;
            case PROVINCE:
                provinceList.add(provinceResult);
                break;
        }
    }

}
