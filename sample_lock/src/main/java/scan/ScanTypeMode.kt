package scan

import androidx.annotation.IntDef


/**
 * by y on 2017/2/16
 */

@IntDef(ScanAppUtils.ALL_APP, ScanAppUtils.NO_SYSTEM_APP, ScanAppUtils.SYSTEM_APP)
@Retention(AnnotationRetention.SOURCE)
internal annotation class ScanTypeMode
