package scan

import rx.Observable
import rx.Subscriber
import scan.listener.ScanListener

/**
 * by y on 2017/2/15
 */

class ScanObservable(private val type: Int, private val listener: ScanListener, private val isNoSystem: Boolean)
    : Observable.OnSubscribe<List<AppModel>> {

    override fun call(subscriber: Subscriber<in List<AppModel>>) {
        subscriber.onNext(QueryApp.getAppInfo(listener.scanContext, type, isNoSystem))
        subscriber.onCompleted()
    }
}
