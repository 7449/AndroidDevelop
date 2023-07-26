package scan

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import scan.listener.ScanListener

/**
 * by y on 2017/2/15
 */

class ScanObservable(
    private val type: Int,
    private val listener: ScanListener,
    private val isNoSystem: Boolean
) : ObservableOnSubscribe<List<AppModel>> {

    override fun subscribe(emitter: ObservableEmitter<List<AppModel>>) {
        emitter.onNext(QueryApp.getAppInfo(listener.scanContext, type, isNoSystem))
        emitter.onComplete()
    }

}
