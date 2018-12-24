package sample.util.develop.android.dagger

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.dagger_activity_main.*
import sample.util.develop.android.dagger.mvp.widget.MVPActivity
import sample.util.develop.android.dagger.singleton.SingletonActivity

/**
 * Dagger2学习还是建议看着自动生成的代码一起学习，这样看的明白点
 */
class DaggerMainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dagger_activity_main)
        btnMvp.setOnClickListener(this)
        btnSingleton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var cls: Class<*>? = null
        val i = v.id
        if (i == R.id.btnMvp) {
            cls = MVPActivity::class.java
        } else if (i == R.id.btnSingleton) {
            cls = SingletonActivity::class.java
        }
        val intent = Intent(v.context, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
