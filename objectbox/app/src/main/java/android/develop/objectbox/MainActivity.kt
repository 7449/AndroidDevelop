package android.develop.objectbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import objectbox.develop.android.multitable.MultiTableMainActivity
import objectbox.develop.android.two.Version2xMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        table.setOnClickListener { startActivity(Intent(this, Version2xMainActivity::class.java)) }
        multiTable.setOnClickListener { startActivity(Intent(this, MultiTableMainActivity::class.java)) }
    }
}
