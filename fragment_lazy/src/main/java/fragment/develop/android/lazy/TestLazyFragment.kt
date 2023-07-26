package fragment.develop.android.lazy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

class TestLazyFragment : LazyFragment() {

    companion object {
        private const val FRAGMENT_INDEX = "fragment_index"
        fun newInstance(position: Int): TestLazyFragment {
            val bundle = Bundle()
            val fragment = TestLazyFragment()
            bundle.putInt(FRAGMENT_INDEX, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var index = 0
    private var progressBar: ProgressBar? = null
    private var text: TextView? = null

    override val layoutId: Int = R.layout.fragment_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments?.getInt(FRAGMENT_INDEX) ?: 0
    }

    override fun initById(convertView: View) {
        progressBar = convertView.findViewById(R.id.progressBar)
        text = convertView.findViewById(R.id.tv)
    }

    private fun progressBar() {
        progressBar?.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar?.visibility = View.GONE
            switchId(index)
        }, 1000)
    }

    private fun switchId(id: Int) {
        val textView = text ?: return
        when (id) {
            0 -> textView.text = "界面一"
            1 -> textView.text = "界面二"
            2 -> textView.text = "界面三"
            3 -> textView.text = "界面四"
            4 -> textView.text = "界面五"
            5 -> textView.text = "界面六"
            6 -> textView.text = "界面七"
        }
    }

    override fun initActivityCreated() {
        progressBar()
    }

}
