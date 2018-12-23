package sample.util.develop.android.error

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.error_activity_main.*
import java.lang.NullPointerException

class ErrorMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error_activity_main)
        btnError.setOnClickListener { throw NullPointerException() }
    }
}
