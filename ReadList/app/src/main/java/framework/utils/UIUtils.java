package framework.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.readlist.R;

import framework.App;


/**
 * by y on 2016/8/7.
 */
public class UIUtils {


    public static Context getContext() {
        return App.getInstance();
    }

    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static View getInflate(Context context, int layout) {
        return View.inflate(context, layout, null);
    }

    public static void startBrowser(Activity activity, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    public static void share(Activity activity, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (activity != null) {
            activity.startActivity(Intent.createChooser(intent, getString(R.string.share)));
        }
    }

    public static void startActivity(Class<?> clz) {
        Intent intent = new Intent(getContext(), clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void Toast(Object object) {
        Toast.makeText(getContext(), object + "", Toast.LENGTH_LONG).show();
    }

    public static void SnackBar(View view, Object object) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT).show();
    }

    public static void SnackBar(View view, Object object, int color) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show();
    }
}
