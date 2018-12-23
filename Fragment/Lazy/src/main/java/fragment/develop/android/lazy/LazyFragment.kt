@file:Suppress("MemberVisibilityCanBePrivate")

package fragment.develop.android.lazy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class LazyFragment : Fragment() {

    private var isLazyVisible = true
    private var isFirstLoad: Boolean = false
    private var isInitView: Boolean = false

    protected abstract val layoutId: Int

    protected abstract fun initById(convertView: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater.inflate(layoutId, container, false)
        isInitView = true
        initById(convertView)
        return convertView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lazyData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isLazyVisible = isVisibleToUser
        if (isVisibleToUser) {
            lazyData()
        }
    }

    protected fun lazyData() {
        if (isFirstLoad || !isLazyVisible || !isInitView) {
            return
        }
        initActivityCreated()
        isFirstLoad = true
    }

    protected abstract fun initActivityCreated()
}
