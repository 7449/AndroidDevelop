package sample.view.develop.android.dotted.line

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.dotted_line_activity_main.*

class DottedLineMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dotted_line_activity_main)
        btnJs.setOnClickListener {
            startActivity(Intent(this@DottedLineMainActivity, DottedLineByJsActivity::class.java))
        }
        btnNaive.setOnClickListener {
            startActivity(
                Intent(
                    this@DottedLineMainActivity,
                    DottedLineByNaiveActivity::class.java
                )
            )
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
