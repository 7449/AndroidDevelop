package com.annotation

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.api.IView
import com.api.ViewBind


/**
 * 如果开启了[kotlin-android-extensions]那还需要ButterKnife吗
 */
class MainActivity : AppCompatActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.t1)
    lateinit var t1: TextView

    @BindView(R.id.t2)
    lateinit var t2: TextView

    @BindString(R.string.app_name)
    lateinit var name: String

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

    @BindBitmap(R.drawable.ic_launcher_background)
    lateinit var bitmap: Bitmap

    @BindBitmap(R.mipmap.ic_launcher)
    lateinit var launcherBitmap: Bitmap

    @BindStringArray(R.array.array_string)
    lateinit var stringArray: Array<String>

    @BindIntArray(R.array.array_int)
    lateinit var intArray: IntArray

    private lateinit var bind: ViewBind<Any>

//    @BindClick(R.id.t1)
//    fun onClickT1(view: View) {
//        Toast.makeText(view.context, "onClickT1", Toast.LENGTH_SHORT).show()
//    }
//
//    @BindClick(R.id.t2)
//    fun onClickT2(view: View) {
//        Toast.makeText(view.context, "onClickT2", Toast.LENGTH_SHORT).show()
//    }
//
//    @BindClick(R.id.t3)
//    fun onClickT3(view: View) {
//        Toast.makeText(view.context, "onClickT3", Toast.LENGTH_SHORT).show()
//    }

    @BindClick(R.id.t1, R.id.t2)
    fun onClicked(view: View) {
        when (view.id) {
            R.id.t1 -> Toast.makeText(view.context, "onClickT1", Toast.LENGTH_SHORT).show()
            R.id.t2 -> Toast.makeText(view.context, "onClickT2", Toast.LENGTH_SHORT).show()
        }
    }

//
//    @BindLongClick(R.id.t1)
//    fun onLongClickT1(view: View): Boolean {
//        Toast.makeText(view.context, "onLongClickT1", Toast.LENGTH_SHORT).show()
//        return true
//    }
//
//    @BindLongClick(R.id.t2)
//    fun onLongClickT2(view: View): Boolean {
//        Toast.makeText(view.context, "onLongClickT2", Toast.LENGTH_SHORT).show()
//        return true
//    }
//
//    @BindLongClick(R.id.t3)
//    fun onLongClickT3(view: View): Boolean {
//        Toast.makeText(view.context, "onLongClickT3", Toast.LENGTH_SHORT).show()
//        return true
//    }

    @BindLongClick(R.id.t1, R.id.t2, R.id.t3)
    fun onLongClicked(view: View): Boolean {
        when (view.id) {
            R.id.t1 -> Toast.makeText(view.context, "onClickT1", Toast.LENGTH_SHORT).show()
            R.id.t2 -> Toast.makeText(view.context, "onClickT2", Toast.LENGTH_SHORT).show()
            R.id.t3 -> Toast.makeText(view.context, "onClickT3", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind = IView.bind(this)
        textView.text = name
        textView.setTextColor(color)
        textView.textSize = simple
        supportFragmentManager.beginTransaction().replace(R.id.fragment, MainFragment()).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        bind.unBind()
    }
}
