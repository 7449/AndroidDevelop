package com.annotation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.api.IView
import com.api.ViewBind

/**
 * by y.
 *
 *
 * Description:
 */
class MainFragment : Fragment() {

    @BindString(R.string.app_name)
    lateinit var appName: String

    @JvmField
    @BindColor(R.color.colorAccent)
    var color: Int = 0

    @JvmField
    @BindDimen(R.dimen.simple)
    var simple: Float = 0F

    @BindDrawable(R.mipmap.ic_launcher)
    lateinit var launcher: Drawable

    @BindStringArray(R.array.array_string)
    lateinit var stringArray: Array<String>

    @BindIntArray(R.array.array_int)
    lateinit var intArray: IntArray

    private lateinit var bind: ViewBind<Any>

    @BindClick(R.id.fragment_root_view)
    fun onClick(view: View) {

    }

    @BindLongClick(R.id.fragment_root_view)
    fun onLongClick(view: View): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_main, container, false)
        bind = IView.bind(this, inflate)
        Log.i(TAG, launcher.toString())
        Log.i(TAG, stringArray.contentToString())
        Log.i(TAG, intArray.contentToString())
        return inflate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.unBind()
    }

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }
}
