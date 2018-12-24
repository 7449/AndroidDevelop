package sample.view.develop.android.wheel;

import android.content.res.AssetManager;
import sample.view.develop.android.wheel.interfaces.XmlManagerInterface;
import sample.view.develop.android.wheel.result.CityResult;
import sample.view.develop.android.wheel.result.DistrictResult;
import sample.view.develop.android.wheel.result.ProvinceResult;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * by y on 2016/10/26
 */

public class XmlManager {

    private static final String PROVINCE_DATA = "province_data.xml";
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<>();
    /**
     * key - 市 values - 区
     */
    private Map<String, String[]> mDistrictDatasMap = new HashMap<>();
    /**
     * key - 区 values - 邮编
     */
    private Map<String, String> mZipcodeDatasMap = new HashMap<>();
    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前区的名称
     */
    private String mCurrentDistrictName = "";
    /**
     * 当前区的邮政编码
     */
    private String mCurrentZipCode = "";
    private XmlManagerInterface xmlManagerInterface = null;


    public XmlManager(XmlManagerInterface xmlManagerInterface) {
        this.xmlManagerInterface = xmlManagerInterface;
    }

    public String[] getProvinceDatas() {
        return mProvinceDatas;
    }

    public void initProvinceDatas(AssetManager assetManager) {
        try {
            InputStream input = assetManager.open(PROVINCE_DATA);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            List<ProvinceResult> provinceList = handler.getDataList();
            // 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {

                mCurrentProviceName = provinceList.get(0).getName();
                List<CityResult> cityList = provinceList.get(0).getCityList();

                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictResult> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
                mProvinceDatas = new String[provinceList.size()];

                for (int i = 0; i < provinceList.size(); i++) {

                    // 遍历所有省的数据
                    mProvinceDatas[i] = provinceList.get(i).getName();
                    cityList = provinceList.get(i).getCityList();
                    String[] cityNames = new String[cityList.size()];


                    for (int j = 0; j < cityList.size(); j++) {

                        // 遍历省下面的所有市的数据
                        cityNames[j] = cityList.get(j).getName();
                        List<DistrictResult> districtList = cityList.get(j).getDistrictList();
                        String[] distrinctNameArray = new String[districtList.size()];
                        DistrictResult[] distrinctArray = new DistrictResult[districtList.size()];


                        for (int k = 0; k < districtList.size(); k++) {

                            // 遍历市下面所有区/县的数据
                            DistrictResult districtResult = new DistrictResult(districtList.get(k).getName(), districtList.get(k).getZipcode());

                            // 区/县对于的邮编，保存到mZipcodeDatasMap
                            mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                            distrinctArray[k] = districtResult;
                            distrinctNameArray[k] = districtResult.getName();
                        }

                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }

                    // 省-市的数据，保存到mCitisDatasMap
                    mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String getCurrentProviceName() {
        return mCurrentProviceName;
    }

    public String getCurrentCityName() {
        return mCurrentCityName;
    }

    public String getCurrentDistrictName() {
        return mCurrentDistrictName;
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    public void updateAreas(int cityCurrent) {
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[cityCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        if (xmlManagerInterface != null) {
            xmlManagerInterface.updateAreas(areas);
        }
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    public void updateCities(int provinceCurrent) {
        mCurrentProviceName = mProvinceDatas[provinceCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        if (xmlManagerInterface != null) {
            xmlManagerInterface.updateCities(cities);
        }
    }

    public void update(int newValue) {
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }
}
