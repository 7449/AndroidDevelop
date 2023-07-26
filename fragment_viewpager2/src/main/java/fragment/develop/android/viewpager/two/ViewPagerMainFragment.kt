package fragment.develop.android.viewpager.two

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ViewPagerMainFragment : Fragment() {

    companion object {
        fun startFragment(position: Int): ViewPagerMainFragment {
            val fragment = ViewPagerMainFragment()
            val bundle = Bundle()
            bundle.putInt("index", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (null != arguments) {
            index = arguments.getInt("index")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager2, container, false)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.tv)
        if (index == 3) {
            textView.setOnClickListener {
                activity?.let {
                    it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                    if (it is ViewPager2MainActivity) {
                        it.currentItem()
                    }
                }
            }
        }
        textView.text = index.toString()
    }

}
