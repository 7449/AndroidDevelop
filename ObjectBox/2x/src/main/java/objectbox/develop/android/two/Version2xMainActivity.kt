package objectbox.develop.android.two

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.two_activity_main.*

class Version2xMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two_activity_main)

        btnObjectBox.setOnClickListener { v ->
            startActivity(
                Intent(
                    v.context,
                    ObjectBox2xActivity::class.java
                )
            )
        }
        btnRxObjectBox.setOnClickListener { v ->
            startActivity(
                Intent(
                    v.context,
                    RxObjectBox2xActivity::class.java
                )
            )
        }
        btnDaoObjectBox.setOnClickListener { v ->
            startActivity(
                Intent(
                    v.context,
                    DaoObjectBox2xActivity::class.java
                )
            )
        }
    }


}
