package com.camerasimple.data;

import com.camerasimple.R;
import com.google.android.cameraview.CameraView;

/**
 * by y on 2016/11/21
 */

public class Constant {
    public static final int[] FLASH_OPTIONS = {
            CameraView.FLASH_AUTO,
            CameraView.FLASH_OFF,
            CameraView.FLASH_ON,
    };

    public static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    public static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };
}
