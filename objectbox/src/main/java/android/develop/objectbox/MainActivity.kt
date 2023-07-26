package android.develop.objectbox

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import objectbox.develop.android.multitable.MultiTableMainActivity
import objectbox.develop.android.two.Version2xMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.table).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Version2xMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.multiTable).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MultiTableMainActivity::class.java
                )
            )
        }
    }
}
