package framework.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * by y on 2016/8/7.
 */
public class RxBus {

    private HashMap<Object, List<Subject>> rxMap;

    private RxBus() {
        rxMap = new HashMap<>();
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
            //noinspection SuspiciousMethodCalls
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                rxMap.remove(tag);
            }
        }
    }

    public <T> Observable<T> toObserverable(@NonNull Object tag) {
        List<Subject> rxList = rxMap.get(tag);
        if (null == rxList) {
            rxList = new ArrayList<>();
            rxMap.put(tag, rxList);
        }
        Subject<T, T> subject = PublishSubject.create();
        rxList.add(subject);
        return subject;
    }
}
