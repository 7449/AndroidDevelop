package sample.view.develop.android.wheel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import sample.view.develop.android.wheel.interfaces.XmlPopupWindowInterface

/**
 * 这里使用popupwindow实现弹窗效果
 *
 *
 * 如果想用DialogFragment实现可参考 NumberPickerView
 */
class WheelMainActivity : AppCompatActivity(), XmlPopupWindowInterface {

    private val tv by lazy { findViewById<TextView>(R.id.tv) }
    private val button by lazy { findViewById<View>(R.id.button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wheel_activity_main)
        button.setOnClickListener {
            XmlPopupWindow(
                window.decorView,
                View.inflate(baseContext, R.layout.popuwindows_region, null),
                this
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(
        currentProviceName: String,
        currentCityName: String,
        currentDistrictName: String
    ) {
        tv.text = "$currentProviceName   $currentCityName  $currentDistrictName"
    }

}
