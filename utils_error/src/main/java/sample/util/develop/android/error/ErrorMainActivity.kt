package sample.util.develop.android.error

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ErrorMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error_activity_main)
        findViewById<View>(R.id.btnError).setOnClickListener { throw NullPointerException() }
    }

}
