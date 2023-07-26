package framework.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * by y on 2017/2/16
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected View mRootView = null;
    protected AlertDialog mAlertDialog = null;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(getCancelable());
        return getDialog();
    }

    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) mRootView.findViewById(id);
    }

    public View getRootView(int id) {
        return View.inflate(getActivity(), id, null);
    }

    public abstract AlertDialog getDialog();


    protected abstract boolean getCancelable();
}
