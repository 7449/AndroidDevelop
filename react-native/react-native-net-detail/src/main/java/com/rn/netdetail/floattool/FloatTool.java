package com.rn.netdetail.floattool;

import android.content.Context;

public class FloatTool {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
