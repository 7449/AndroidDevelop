package com.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.appcompat.widget.AppCompatTextView;

import com.common.R;

/**
 * by y.
 * <p>
 * Description:
 */
public class Progress extends Dialog {
    private AppCompatTextView progressMessage;

    public Progress(Context context) {
        super(context);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setContentView(R.layout.layout_progress);
        progressMessage = findViewById(R.id.progress_message);
        setCanceledOnTouchOutside(false);
    }

    public Progress setMessage(String message) {
        progressMessage.setText(message);
        progressMessage.setVisibility(View.VISIBLE);
        return this;
    }
}
