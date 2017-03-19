package com.numberpickerview.sample.numberpicker.handler;

import android.os.Handler;
import android.os.Message;

import com.numberpickerview.sample.numberpicker.NumberPickerDefault;

/**
 * by y on 2017/3/16
 */

public class PickerInMainHandler extends Handler {

    private PickerInMainListener listener;

    public PickerInMainHandler(PickerInMainListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (listener == null) {
            return;
        }
        switch (msg.what) {
            case NumberPickerDefault.HANDLER_WHAT_REQUEST_LAYOUT:
                listener.onPickerInMainRequestLayout();
                break;
            case NumberPickerDefault.HANDLER_WHAT_LISTENER_VALUE_CHANGED:
                listener.onPickerInMainValueChanged(msg.arg1, msg.arg2, msg.obj);
                break;
        }
    }

    public interface PickerInMainListener {
        void onPickerInMainRequestLayout();

        void onPickerInMainValueChanged(int arg1, int arg2, Object obj);
    }
}
