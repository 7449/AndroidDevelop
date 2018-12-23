package sample.util.develop.android.splash

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.splash_root_layout.*

/**
 * SplashFragment实现
 *
 *
 * 1. 通过 replace remove Fragment去实现Splash页面ImageView的显示,并且进一步使用ViewStub优化进入首页的效率
 *
 *
 * 2. 进来Splash的时候可加载一些初始化数据，也可以请求网络数据。
 *
 *
 * 3.也可以让用户选择是否显示Splash. 例如BiliBili
 */
class SplashMainActivity : AppCompatActivity() {

    private val isSplash = true //增加标志 是否显示SplashFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_root_layout)

        if (isSplash) {
            val splashFragment = SplashFragment.splash
            Toast.makeText(this, "加载 SplashFragment,此时可直接请求网络数据", Toast.LENGTH_SHORT).show()
            replaceFragment(splashFragment)
            Handler().postDelayed({
                removeFragment(splashFragment)
                Toast.makeText(applicationContext, "remove SplashFragment, viewStub Inflate 显示数据", Toast.LENGTH_SHORT)
                    .show()
                showMain()
            }, 3000)
        } else {
            showMain()
        }

    }

    private fun showMain() {
        val inflate = view_stub.inflate()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.activity_main, fragment).commit()
    }

    private fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}
