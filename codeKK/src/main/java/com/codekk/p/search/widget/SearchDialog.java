package com.codekk.p.search.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.codekk.p.R;

import framework.utils.UIUtils;

/**
 * by y on 2016/8/7.
 */
public class SearchDialog {


    public static void startSearch(final Activity activity, final SearchInterface searchInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View editView = UIUtils.getInflate(R.layout.search_layout);
        final EditText editText = (EditText) editView.findViewById(R.id.et_search);
        builder.setView(editView);
        builder.setPositiveButton(UIUtils.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                searchInterface.startSearch(editText.getText().toString().trim());
                UIUtils.offKeyboard();
            }
        });
        builder.setNegativeButton(UIUtils.getString(R.string.dialog_finish),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }


    public interface SearchInterface {
        void startSearch(String search);
    }

}
