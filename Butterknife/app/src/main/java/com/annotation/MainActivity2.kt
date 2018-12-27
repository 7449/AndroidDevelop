package com.annotation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.api.IView
import com.api.ViewBind

class MainActivity2 : AppCompatActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.t1)
    lateinit var t1: TextView

    @BindDrawable(R.drawable.ic_launcher_background)
    lateinit var launcher: Drawable

    @BindStringArray(R.array.array_string)
    lateinit var stringArray: Array<String>

    @BindIntArray(R.array.array_int)
    lateinit var intArray: IntArray

    private lateinit var bind: ViewBind<Any>

    @BindClick(R.id.t1)
    fun onClickT1(view: View) {
        Toast.makeText(view.context, "onClickT1", Toast.LENGTH_SHORT).show()
    }

    @BindClick(R.id.t2)
    fun onClickT2(view: View) {
        Toast.makeText(view.context, "onClickT2", Toast.LENGTH_SHORT).show()
    }

    @BindClick(R.id.t3)
    fun onClickT3(view: View) {
        Toast.makeText(view.context, "onClickT3", Toast.LENGTH_SHORT).show()
    }


    @BindLongClick(R.id.t1)
    fun onLongClickT1(view: View): Boolean {
        Toast.makeText(view.context, "onLongClickT1", Toast.LENGTH_SHORT).show()
        return true
    }

    @BindLongClick(R.id.t2)
    fun onLongClickT2(view: View): Boolean {
        Toast.makeText(view.context, "onLongClickT2", Toast.LENGTH_SHORT).show()
        return true
    }

    @BindLongClick(R.id.t3)
    fun onLongClickT3(view: View): Boolean {
        Toast.makeText(view.context, "onLongClickT3", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind = IView.bind(this)
        textView.setText(R.string.app_name)
    }

    override fun onDestroy() {
        super.onDestroy()
        bind.unBind()
    }
}
