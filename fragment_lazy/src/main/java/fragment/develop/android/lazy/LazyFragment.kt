package fragment.develop.android.lazy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class LazyFragment : Fragment() {

    private var isLazyVisible = true
    private var isFirstLoad: Boolean = false
    private var isInitView: Boolean = false

    protected abstract val layoutId: Int

    protected abstract fun initById(convertView: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val convertView = inflater.inflate(layoutId, container, false)
        isInitView = true
        initById(convertView)
        return convertView
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lazyData()
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isLazyVisible = isVisibleToUser
        if (isVisibleToUser) {
            lazyData()
        }
    }

    private fun lazyData() {
        if (isFirstLoad || !isLazyVisible || !isInitView) {
            return
        }
        initActivityCreated()
        isFirstLoad = true
    }

    protected abstract fun initActivityCreated()

}
