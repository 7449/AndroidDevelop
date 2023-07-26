package sample.view.develop.android.numberpicker.widget.handler;

import android.os.HandlerThread;

/**
 * by y on 2017/3/16
 */

public class PickerHandlerThread extends HandlerThread {

    public static final String PICKER_HANDLER_NAME = "HandlerThread-For-Refreshing";


    public PickerHandlerThread(String name) {
        super(name);
    }
}
