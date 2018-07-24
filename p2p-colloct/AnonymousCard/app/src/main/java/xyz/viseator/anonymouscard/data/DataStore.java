package xyz.viseator.anonymouscard.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by viseator on 2016/12/23.
 * Wudi
 * viseator@gmail.com
 */

public class DataStore {
    private static final String TAG = "wudi DataStore";
    private ArrayList<DataPackage> dataPackages;

    public ArrayList<DataPackage> getDataPackages() {
        return dataPackages;
    }

    public void setDataPackages(ArrayList<DataPackage> dataPackages) {
        this.dataPackages = dataPackages;
    }

    public DataPackage getDataById(String id) {
        for (DataPackage dataPackage : dataPackages) {
            if (Objects.equals(dataPackage.getId(), id)) {
                Log.d(TAG, "getDataById: got the id data");
                return dataPackage;
            }
        }
        return null;
    }
}
