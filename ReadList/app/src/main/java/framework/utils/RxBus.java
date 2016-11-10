package framework.utils;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * by y on 2016/8/7.
 */
public class RxBus {

    private ArrayMap<Object, List<Subject>> rxMap;
    private List<Subject> rxList;

    private RxBus() {
        rxMap = new ArrayMap<>();
    }

    public static RxBus getInstance() {
        return RxbusHolder.rxBus;
    }

    private static class RxbusHolder {
        private static final RxBus rxBus = new RxBus();
    }

    public void send(@NonNull Object tag) {
        send(tag, "");
    }

    public void send(@NonNull Object tag, @NonNull Object object) {
        List<Subject> subjects = rxMap.get(tag);
        if (null != subjects && !subjects.isEmpty()) {
            for (Subject s : subjects) {
                s.onNext(object);
            }
        }
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjects = rxMap.get(tag);
        if (null != subjects) {
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                rxMap.remove(tag);
            }
        }
    }

    public <T> Observable<T> toObserverable(@NonNull Object tag) {
        rxList = rxMap.get(tag);
        if (null == rxList) {
            rxList = new ArrayList<>();
            rxMap.put(tag, rxList);
        }
        Subject<T, T> subject = PublishSubject.create();
        rxList.add(subject);
        return subject;
    }

    public void clearAllRxBus() {
        if (rxList != null) {
            rxList.clear();
        }
        if (rxMap != null) {
            rxMap.clear();
        }
    }
}