package scan.listener;

import android.content.Context;

import java.util.List;

import scan.AppModel;

/**
 * by y on 2017/2/15
 */

public interface ScanListener {
    void onScanStart();

    void onScanSuccess(List<AppModel> data);

    void onScanError(Throwable e);

    Context getScanContext();
}
