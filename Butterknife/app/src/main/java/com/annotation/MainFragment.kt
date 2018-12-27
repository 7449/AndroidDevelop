package com.annotation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.api.IView
import com.api.ViewBind
import java.util.*

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

    @BindDrawable(R.drawable.ic_launcher_background)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_main, container, false)
        bind = IView.bind(this, inflate)
        Log.i(TAG, launcher.toString())
        Log.i(TAG, Arrays.toString(stringArray))
        Log.i(TAG, Arrays.toString(intArray))
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
