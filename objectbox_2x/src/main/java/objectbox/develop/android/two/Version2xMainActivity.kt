package objectbox.develop.android.two

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Version2xMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two_activity_main)

        findViewById<View>(R.id.btnObjectBox).setOnClickListener { v ->
            startActivity(
                Intent(
                    v.context,
                    ObjectBox2xActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btnRxObjectBox).setOnClickListener { v ->
            startActivity(
                Intent(
                    v.context,
                    RxObjectBox2xActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btnDaoObjectBox).setOnClickListener { v ->
            startActivity(
                Intent(
                    v.context,
                    DaoObjectBox2xActivity::class.java
                )
            )
        }
    }


}
