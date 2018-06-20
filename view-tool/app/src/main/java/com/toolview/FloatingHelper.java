package com.toolview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Observable;
import java.util.Observer;

public class FloatingHelper implements Observer, ObservableCall {
    private PositionObservable observable = PositionObservable.getInstance();
    private FloatingDraggedView toolView;

    @SuppressLint("InflateParams")
    public FloatingHelper(Context context, int rootView) {
        View floatingView = LayoutInflater.from(context).inflate(R.layout.layout_floating_dragged, null);
        toolView = new FloatingDraggedView(context);
        toolView.addView(View.inflate(context, rootView, null), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        toolView.addView(floatingView, new FrameLayout.LayoutParams(FloatTool.dip2px(context, 30), FloatTool.dip2px(context, 30)));
        observable.addObserver(this);
        toolView.setObservableCall(this);
    }

    public View getView() {
        return toolView;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (toolView != null) {
            toolView.restorePosition();
        }
    }

    @Override
    public PositionObservable getObservable() {
        return observable;
    }

    @Override
    public FloatingHelper getFloatingHelper() {
        return this;
    }
}
