package sample.view.develop.android.numberpicker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import sample.view.develop.android.numberpicker.widget.city.EasyCityListener
import sample.view.develop.android.numberpicker.widget.city.EasyCityView
import sample.view.develop.android.numberpicker.widget.radio.EasyPickerListener
import sample.view.develop.android.numberpicker.widget.radio.EasyPickerView

class NumberPickerMainActivity : AppCompatActivity(), EasyPickerListener, EasyCityListener {
    private val btnNumber by lazy { findViewById<View>(R.id.btnNumber) }
    private val btnCity by lazy { findViewById<View>(R.id.btnCity) }
    private val text by lazy { findViewById<TextView>(R.id.text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.number_picker_activity_main)
        btnNumber.setOnClickListener { initEasyFragment() }
        btnCity.setOnClickListener { initEasyCityDialog() }
    }

    private fun initEasyCityDialog() {
        EasyCityView.Builder(this)
            .setCancelable(true)
            .setTitle("请选择所在城市")
            .setProvinceName("陕西省") // 默认选中省
            .setCityName("西安市") //默认选中市
            .setAreaName("雁塔区") //默认选中区
            .setDividerColor(R.color.colorPrimary)
            .setSelectTextColor(R.color.colorPrimary)
            .show(supportFragmentManager, "city")
    }

    private fun initEasyFragment() {
        val value = arrayOfNulls<String>(200)
        for (i in value.indices) {
            value[i] = i.toString()
        }
        EasyPickerView.Builder(this)
            .setTextArray(value)
            .setCancelable(true)
            .setTitle("请选择身高")
            .setHintText("cm")
            .setValue(150)
            .setHintTextColor(R.color.colorPrimary)
            .setDividerColor(R.color.colorPrimary)
            .setSelectTextColor(R.color.colorPrimary)
            .show(supportFragmentManager, "easy")
    }

    override fun onEasyCancel() {

    }

    @SuppressLint("SetTextI18n")
    override fun onEasyNext(provinceValue: String, cityValue: String, areaValue: String) {
        text.text = "$provinceValue   $cityValue   $areaValue"
    }

    override fun onEasyNext(value: String) {
        text.text = value
    }
}
