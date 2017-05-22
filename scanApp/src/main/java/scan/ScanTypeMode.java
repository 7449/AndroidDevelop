package scan;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/2/16
 */

@IntDef({ScanAppUtils.ALL_APP, ScanAppUtils.NO_SYSTEM_APP, ScanAppUtils.SYSTEM_APP})
@Retention(RetentionPolicy.SOURCE)
@interface ScanTypeMode {
}
