package com.rn.netdetail.floattool;

import java.util.Observable;

public class PositionObservable extends Observable {

    private static PositionObservable sInstance;

    static PositionObservable getInstance() {
        if (sInstance == null) {
            sInstance = new PositionObservable();
        }
        return sInstance;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}