package fragment.develop.android.lazy

import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.fragment_test.*

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

    override val layoutId: Int = R.layout.fragment_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            index = bundle.getInt(FRAGMENT_INDEX)
        }
    }

    override fun initById(convertView: View) {
    }

    private fun progressBar() {
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            progressBar.visibility = View.GONE
            switchId(index)
        }, 1000)
    }

    private fun switchId(id: Int) {
        when (id) {
            0 -> tv.text = "界面一"
            1 -> tv.text = "界面二"
            2 -> tv.text = "界面三"
            3 -> tv.text = "界面四"
            4 -> tv.text = "界面五"
            5 -> tv.text = "界面六"
            6 -> tv.text = "界面七"
        }

    }

    override fun initActivityCreated() {
        progressBar()
    }
}
