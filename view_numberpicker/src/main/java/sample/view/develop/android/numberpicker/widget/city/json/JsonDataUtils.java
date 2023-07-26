package sample.view.develop.android.numberpicker.widget.city.json;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sample.view.develop.android.numberpicker.widget.city.bean.ProvinceBean;

/**
 * by y on 2017/4/3.
 * <p>
 * <p>
 * 单例 保证数据一致性
 */

public class JsonDataUtils {

    private static final String CITY_NAME = "province.json";
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    private Map<String, String[]> mCityMap = new HashMap<>();
    /**
     * key - 市 values - 区
     */
    private Map<String, String[]> mAreaMap = new HashMap<>();
    private int provinceValue = 0;
    private int cityValue = 0;
    private int areaValue = 0;

    private JsonDataUtils() {
    }

    public static JsonDataUtils get() {
        return JsonDataHolder.jsonDataUtils;
    }

    public String[] getProvinceDatas() {
        return mProvinceDatas;
    }

    public String[] getCity(String key) {
        return mCityMap.get(key);
    }

    public String[] getArea(String key) {
        return mAreaMap.get(key);
    }

    public int getProvinceValue() {
        return provinceValue;
    }

    public int getCityValue() {
        return cityValue;
    }

    public int getAreaValue() {
        return areaValue;
    }

    public void initValue(String provinceName, String cityName, String areaName) {
        String[] provinceDatas = getProvinceDatas();
        int provinceSize = provinceDatas.length;
        for (int i = 0; i < provinceSize; i++) {
            if (TextUtils.equals(provinceName, provinceDatas[i])) {
                provinceValue = i;

                String[] city = JsonDataUtils.get().getCity(provinceName);

                int citySize = city.length;

                for (int j = 0; j < citySize; j++) {

                    if (TextUtils.equals(cityName, city[j])) {
                        cityValue = j;

                        String[] area = JsonDataUtils.get().getArea(cityName);

                        int areaSize = area.length;

                        for (int k = 0; k < areaSize; k++) {

                            if (TextUtils.equals(areaName, area[k])) {
                                areaValue = k;
                            }
                        }

                    }

                }
            }
        }
    }

    public void init(Context context) {
        ArrayList<ProvinceBean> provinceBeen = parseData(context);

        if (provinceBeen != null && !provinceBeen.isEmpty()) {
            //初始化所有省的value，长度为 provinceBeen.size
            mProvinceDatas = new String[provinceBeen.size()];

            int provinceSize = provinceBeen.size();
            for (int i = 0; i < provinceSize; i++) {

                // 为 省 value 赋值
                mProvinceDatas[i] = provinceBeen.get(i).getName();

                //获取 市 数据
                List<ProvinceBean.CityBean> cityList = provinceBeen.get(i).getCity();
                //初始化所有市的value，长度为 cityList.size
                String[] cityNames = new String[cityList.size()];

                int citySize = cityList.size();
                for (int j = 0; j < citySize; j++) {

                    // 为 市 value 赋值
                    cityNames[j] = cityList.get(j).getName();

                    //获取 区 数据
                    List<String> areaList = cityList.get(j).getArea();

                    //初始化所有区的value，长度为 districtList.size
                    String[] areaNameArray = new String[areaList.size()];
                    // 市-区/县的数据，保存到mDistrictDatasMap

                    int areaSize = areaList.size();
                    for (int k = 0; k < areaSize; k++) {
                        areaNameArray[k] = areaList.get(k);
                    }
                    mAreaMap.put(cityNames[j], areaNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCityMap.put(provinceBeen.get(i).getName(), cityNames);
            }
        }

    }

    private ArrayList<ProvinceBean> parseData(Context context) {
        ArrayList<ProvinceBean> jsonBeen = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(GetJson.getJson(context, CITY_NAME));
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                jsonBeen.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonBeen;
    }

    private static final class JsonDataHolder {
        private static final JsonDataUtils jsonDataUtils = new JsonDataUtils();
    }
}
