package select.image.com.crop;

import android.app.Activity;
import android.net.Uri;

/**
 * by y on 2017/4/7.
 */

public interface CropCallBack {
    Activity getCropActivity();

    void onCropCancel();

    void onCropSuccess(Uri uri);

    void onCropError(String errorMessage);

}
