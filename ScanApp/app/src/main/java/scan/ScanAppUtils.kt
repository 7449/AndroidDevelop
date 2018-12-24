package scan

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import scan.listener.ScanListener

/**
 * by y on 2016/10/20.
 */

class ScanAppUtils {

    companion object {
        const val ALL_APP = 1 //全部app
        const val SYSTEM_APP = 2 //系统app
        const val NO_SYSTEM_APP = 3 //第三方app
        val instance by lazy { ScanAppUtils() }
    }

    private lateinit var subscription: Subscription

    /**
     * true:系统app更新后也算在第三方app里面
     * false:只记录用户安装的app
     */
    private var isNoSystem = false

    fun setNoSystem(noSystem: Boolean): ScanAppUtils {
        isNoSystem = noSystem
        return this
    }

    fun start(@ScanTypeMode type: Int, scanListener: ScanListener) {
        unsubscribe()
        subscription = Observable
                .create(ScanObservable(type, scanListener, isNoSystem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<AppModel>>() {

                    override fun onStart() {
                        super.onStart()
                        scanListener.onScanStart()
                    }

                    override fun onCompleted() {
                        unsubscribe()
                    }

                    override fun onError(e: Throwable) {
                        unsubscribe()
                        scanListener.onScanError(e)
                    }

                    override fun onNext(list: List<AppModel>) {
                        scanListener.onScanSuccess(list)
                    }
                })
    }


    private fun unsubscribe() {
        if (::subscription.isInitialized && !subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }
}
