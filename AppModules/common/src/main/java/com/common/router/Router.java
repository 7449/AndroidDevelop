package com.common.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Router {
    private SimpleArrayMap<String, Class<? extends Activity>> routerActivityMap = new SimpleArrayMap<>();
    private Bundle bundle = new Bundle();
    private String key;
    private int enterAnim = -1;
    private int exitAnim = -1;


    private Router() {
    }

    private static final class RouterHolder {
        private static final Router router = new Router();
    }

    public static Router getInstance() {
        return RouterHolder.router;
    }

    public void init(IRouter iRouter) {
        iRouter.registerActivity(routerActivityMap);
    }

    public Router with(@NonNull String key) {
        this.key = key;
        bundle.clear();
        return this;
    }

    public Router withBundle(Bundle bundle) {
        bundle.putAll(bundle);
        return this;
    }


    public Router withBoolean(@Nullable String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }


    public Router withInt(@Nullable String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public Router withLong(@Nullable String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public Router withDouble(@Nullable String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }


    public Router withBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        bundle.putBooleanArray(key, value);
        return this;
    }


    public Router withIntArray(@Nullable String key, @Nullable int[] value) {
        bundle.putIntArray(key, value);
        return this;
    }


    public Router withLongArray(@Nullable String key, @Nullable long[] value) {
        bundle.putLongArray(key, value);
        return this;
    }


    public Router withDoubleArray(@Nullable String key, @Nullable double[] value) {
        bundle.putDoubleArray(key, value);
        return this;
    }


    public Router withStringArray(@Nullable String key, @Nullable String[] value) {
        bundle.putStringArray(key, value);
        return this;
    }


    public Router withByte(@Nullable String key, byte value) {
        bundle.putByte(key, value);
        return this;
    }

    public Router withChar(@Nullable String key, char value) {
        bundle.putChar(key, value);
        return this;
    }

    public Router withShort(@Nullable String key, short value) {
        bundle.putShort(key, value);
        return this;
    }

    public Router withString(@Nullable String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public Router withFloat(@Nullable String key, float value) {
        bundle.putFloat(key, value);
        return this;
    }

    public Router withCharSequence(@Nullable String key, @Nullable CharSequence value) {
        bundle.putCharSequence(key, value);
        return this;
    }

    public Router withParcelable(@Nullable String key, @Nullable Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public Router withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        bundle.putParcelableArray(key, value);
        return this;
    }

    public Router withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
        return this;
    }

    public Router withSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);
        return this;
    }

    public Router withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);
        return this;
    }

    public Router withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        bundle.putStringArrayList(key, value);
        return this;
    }

    public Router withCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }

    public Router withSerializable(@Nullable String key, @Nullable Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }

    public Router withByteArray(@Nullable String key, @Nullable byte[] value) {
        bundle.putByteArray(key, value);
        return this;
    }

    public Router withShortArray(@Nullable String key, @Nullable short[] value) {
        bundle.putShortArray(key, value);
        return this;
    }

    public Router withCharArray(@Nullable String key, @Nullable char[] value) {
        bundle.putCharArray(key, value);
        return this;
    }

    public Router withFloatArray(@Nullable String key, @Nullable float[] value) {
        bundle.putFloatArray(key, value);
        return this;
    }

    public Router withCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);
        return this;
    }

    public Router withBundle(@Nullable String key, @Nullable Bundle value) {
        bundle.putBundle(key, value);
        return this;
    }

    public Router withBinder(@Nullable String key, @Nullable IBinder value) {
        bundle.putBinder(key, value);
        return this;
    }

    public Router withTransition(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = enterAnim;
        return this;
    }

    public void navigation(Context context) {
        Intent intent = new Intent(context, routerActivityMap.get(key));
        if (context instanceof Activity) {
            if (enterAnim != -1 && exitAnim != -1)
                ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
