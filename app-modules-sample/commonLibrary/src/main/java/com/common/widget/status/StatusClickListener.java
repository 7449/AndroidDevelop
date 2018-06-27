package com.common.widget.status;

/**
 * by y on 14/07/2017.
 */

public interface StatusClickListener {
    void onNorMalClick();

    void onLoadingClick();

    void onEmptyClick();

    void onSuccessClick();

    void onErrorClick();
}
