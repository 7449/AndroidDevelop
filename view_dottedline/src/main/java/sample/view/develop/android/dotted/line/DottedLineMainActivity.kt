package sample.view.develop.android.dotted.line

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DottedLineMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dotted_line_activity_main)
        val btnJs = findViewById<View>(R.id.btnJs)
        btnJs.setOnClickListener {
            startActivity(Intent(this@DottedLineMainActivity, DottedLineByJsActivity::class.java))
        }
    }

}
