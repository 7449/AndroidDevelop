package com.xadapter.sample.status;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * by y on 14/07/2017.
 */

class Util {
    private Util() {
    }

    static View getViewLayout(@NonNull StatusLayout statusLayout, @LayoutRes int id) {
        return LayoutInflater.from(statusLayout.getContext()).inflate(id, statusLayout, false);
    }


    static FrameLayout.LayoutParams getParams() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
    }

    static void goneView(@NonNull View... views) {
        for (View view : views) {
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }

    static void visibilityView(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
