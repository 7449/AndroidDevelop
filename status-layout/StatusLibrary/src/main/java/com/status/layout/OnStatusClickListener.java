package com.status.layout;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * by y on 14/07/2017.
 */

public interface OnStatusClickListener {
    void onNorMalClick(@NonNull View view);

    void onLoadingClick(@NonNull View view);

    void onEmptyClick(@NonNull View view);

    void onSuccessClick(@NonNull View view);

    void onErrorClick(@NonNull View view);
}
