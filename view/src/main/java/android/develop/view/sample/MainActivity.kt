package android.develop.view.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sample.view.develop.android.dotted.line.DottedLineMainActivity
import sample.view.develop.android.numberpicker.NumberPickerMainActivity
import sample.view.develop.android.refresh.RefreshMainActivity
import sample.view.develop.android.shortcuts.ShortcutsMainActivity
import sample.view.develop.android.wheel.WheelMainActivity

class MainActivity : AppCompatActivity() {

    private val refresh by lazy { findViewById<View>(R.id.refresh) }
    private val numberPicker by lazy { findViewById<View>(R.id.numberPicker) }
    private val wheel by lazy { findViewById<View>(R.id.wheel) }
    private val dottedLine by lazy { findViewById<View>(R.id.dottedLine) }
    private val shortcuts by lazy { findViewById<View>(R.id.shortcuts) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refresh.setOnClickListener {
            startActivity(Intent(this, RefreshMainActivity::class.java))
        }
        numberPicker.setOnClickListener {
            startActivity(Intent(this, NumberPickerMainActivity::class.java))
        }
        wheel.setOnClickListener {
            startActivity(Intent(this, WheelMainActivity::class.java))
        }
        dottedLine.setOnClickListener {
            startActivity(Intent(this, DottedLineMainActivity::class.java))
        }
        shortcuts.setOnClickListener {
            startActivity(Intent(this, ShortcutsMainActivity::class.java))
        }
    }
}
