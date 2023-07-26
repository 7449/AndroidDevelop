package android.develop.greendao

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import greendao.develop.android.external.ExternalMainActivity
import greendao.develop.android.multi.table.MultiTableMainActivity
import greendao.develop.android.three.Version3XMainActivity
import greendao.develop.android.two.Version2XMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.version2x).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Version2XMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.version3x).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Version3XMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.external).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ExternalMainActivity::class.java
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
