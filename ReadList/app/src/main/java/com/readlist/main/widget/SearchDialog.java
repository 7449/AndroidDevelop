package com.readlist.main.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.readlist.R;

import framework.base.BaseDialog;
import framework.data.Constant;

/**
 * by y on 2016/11/9
 */

public class SearchDialog extends BaseDialog implements View.OnClickListener {

    private SearchDialogInterface searchDialogInterface;
    private EditText editText;

    public static void showDialog(Context context, SearchDialogInterface searchDialogInterface) {
        new SearchDialog(context, searchDialogInterface);
    }

    protected SearchDialog(@NonNull Context context, SearchDialogInterface searchDialogInterface) {
        super(context);
        this.searchDialogInterface = searchDialogInterface;
    }

    @Override
    protected void initCreate() {

    }

    @Override
    protected void initById() {
        getView(R.id.search_btn).setOnClickListener(this);
        TextInputLayout textInputLayout = getView(R.id.search_text_et);
        editText = textInputLayout.getEditText();
    }

    @Override
    protected String getTitle() {
        return Constant.SEARCH_TITLE;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                if (searchDialogInterface != null) {
                    String word = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(word)) {
                        searchDialogInterface.wordEmpty();
                    } else {
                        searchDialogInterface.enterWord(word);
                    }
                }
                dismiss();
                break;
        }
    }

    public interface SearchDialogInterface {
        void enterWord(String word);

        void wordEmpty();
    }
}
