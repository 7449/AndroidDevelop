package android.develop.view.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sample.view.develop.android.dotted.line.DottedLineMainActivity
import sample.view.develop.android.numberpicker.NumberPickerMainActivity
import sample.view.develop.android.refresh.RefreshMainActivity
import sample.view.develop.android.shortcuts.ShortcutsMainActivity
import sample.view.develop.android.wheel.WheelMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refresh.setOnClickListener { startActivity(Intent(this, RefreshMainActivity::class.java)) }
        numberPicker.setOnClickListener { startActivity(Intent(this, NumberPickerMainActivity::class.java)) }
        wheel.setOnClickListener { startActivity(Intent(this, WheelMainActivity::class.java)) }
        dottedLine.setOnClickListener { startActivity(Intent(this, DottedLineMainActivity::class.java)) }
        shortcuts.setOnClickListener { startActivity(Intent(this, ShortcutsMainActivity::class.java)) }
    }
}
