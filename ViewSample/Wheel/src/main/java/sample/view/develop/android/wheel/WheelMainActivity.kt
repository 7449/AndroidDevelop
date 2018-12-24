package sample.view.develop.android.wheel

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.wheel_activity_main.*
import sample.view.develop.android.wheel.interfaces.XmlPopupWindowInterface

/**
 * 这里使用popupwindow实现弹窗效果
 *
 *
 * 如果想用DialogFragment实现可参考 NumberPickerView
 */
class WheelMainActivity : AppCompatActivity(), XmlPopupWindowInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wheel_activity_main)
        button.setOnClickListener {
            XmlPopupWindow(View.inflate(baseContext, R.layout.popuwindows_region, null), this)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(currentProviceName: String, currentCityName: String, currentDistrictName: String) {
        tv.text = "$currentProviceName   $currentCityName  $currentDistrictName"
    }

}
