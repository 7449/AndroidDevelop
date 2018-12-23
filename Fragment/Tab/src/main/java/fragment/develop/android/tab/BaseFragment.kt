package fragment.develop.android.tab

import android.content.res.Configuration
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //避免横竖屏切换时页面重叠，对应的activity添加android:configChanges = "orientation|screenSize"
    }
}
