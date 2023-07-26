package com.common.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import io.reactivex.annotations.NonNull;

public class KeyboardStatusDetector implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 100;
    private boolean keyboardVisible = false;
    private KeyboardListener keyboardListener;

    public KeyboardStatusDetector(@NonNull KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        keyboardListener.getKeyboardView().getWindowVisibleDisplayFrame(r);
        int heightDiff = keyboardListener.getKeyboardView().getRootView().getHeight() - (r.bottom - r.top);
        if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) {
            if (!keyboardVisible) {
                keyboardVisible = true;
                keyboardListener.onVisibilityChanged(true);
            }
        } else {
            if (keyboardVisible) {
                keyboardVisible = false;
                keyboardListener.onVisibilityChanged(false);
            }
        }
    }


    public interface KeyboardListener {
        View getKeyboardView();

        void onVisibilityChanged(boolean flag);
    }
}
