package com.numberpickerview.sample.numberpicker;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;

/**
 * by y on 2017/3/16
 * <p>
 * NumBerPickerView 工具类
 */

public class NumberPickerUtils {


    /**
     * 获取NumberPicker data
     */
    public static String[] convertCharSequenceArrayToStringArray(CharSequence[] charSequences) {
        if (charSequences == null) return null;
        String[] ret = new String[charSequences.length];
        for (int i = 0; i < charSequences.length; i++) {
            ret[i] = charSequences[i].toString();
        }
        return ret;
    }


    public static float getEvaluateSize(float fraction, float startSize, float endSize) {
        return startSize + (endSize - startSize) * fraction;
    }


    public static int getEvaluateColor(float fraction, int startColor, int endColor) {

        int a, r, g, b;

        int sA = (startColor & 0xff000000) >>> 24;
        int sR = (startColor & 0x00ff0000) >>> 16;
        int sG = (startColor & 0x0000ff00) >>> 8;
        int sB = (startColor & 0x000000ff);

        int eA = (endColor & 0xff000000) >>> 24;
        int eR = (endColor & 0x00ff0000) >>> 16;
        int eG = (endColor & 0x0000ff00) >>> 8;
        int eB = (endColor & 0x000000ff);

        a = (int) (sA + (eA - sA) * fraction);
        r = (int) (sR + (eR - sR) * fraction);
        g = (int) (sG + (eG - sG) * fraction);
        b = (int) (sB + (eB - sB) * fraction);

        return a << 24 | r << 16 | g << 8 | b;
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float densityScale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * densityScale + 0.5f);
    }

    public static boolean isStringEqual(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }

    public static Message getMsg(int what) {
        return getMsg(what, 0, 0, null);
    }

    public static Message getMsg(int what, int arg1, int arg2, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        return msg;
    }


    public static int refineValueByLimit(int value, int minValue, int maxValue, boolean wrap, int oneRecyclerSize) {
        if (wrap) {
            if (value > maxValue) {
                value = (value - maxValue) % oneRecyclerSize + minValue - 1;
            } else if (value < minValue) {
                value = (value - minValue) % oneRecyclerSize + maxValue + 1;
            }
            return value;
        } else {
            if (value > maxValue) {
                value = maxValue;
            } else if (value < minValue) {
                value = minValue;
            }
            return value;
        }
    }

    public static TextUtils.TruncateAt getEllipsizeType(String mTextEllipsize) {
        switch (mTextEllipsize) {
            case NumberPickerDefault.TEXT_ELLIPSIZE_START:
                return TextUtils.TruncateAt.START;
            case NumberPickerDefault.TEXT_ELLIPSIZE_MIDDLE:
                return TextUtils.TruncateAt.MIDDLE;
            case NumberPickerDefault.TEXT_ELLIPSIZE_END:
                return TextUtils.TruncateAt.END;
            default:
                throw new IllegalArgumentException("Illegal text ellipsize type.");
        }
    }

    public static int getIndexByRawIndex(int index, int size, boolean wrap) {
        if (size <= 0) return 0;
        if (wrap) {
            index = index % size;
            if (index < 0) {
                index = index + size;
            }
            return index;
        } else {
            return index;
        }
    }

}
