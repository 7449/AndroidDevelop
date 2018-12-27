package android.develop.greendao

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import greendao.develop.android.external.ExternalMainActivity
import greendao.develop.android.multi.table.MultiTableMainActivity
import greendao.develop.android.three.Version3XMainActivity
import greendao.develop.android.two.Version2XMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        version2x.setOnClickListener { startActivity(Intent(this, Version2XMainActivity::class.java)) }
        version3x.setOnClickListener { startActivity(Intent(this, Version3XMainActivity::class.java)) }
        external.setOnClickListener { startActivity(Intent(this, ExternalMainActivity::class.java)) }
        multiTable.setOnClickListener { startActivity(Intent(this, MultiTableMainActivity::class.java)) }
    }
}
