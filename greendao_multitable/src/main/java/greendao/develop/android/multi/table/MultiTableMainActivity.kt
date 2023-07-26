package greendao.develop.android.multi.table

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import greendao.develop.android.multi.table.one.MultiTableGreenDao1Activity
import greendao.develop.android.multi.table.two.MultiTableGreenDao2Activity

/**
 * 由于关联查询是懒加载，会优先从缓存中处理，
 * 所以更新数据和删除数据 可以使用 resetXXX()  重置一对多的关系，使下一个获取调用查询一个新的结果。
 *
 *
 * 有时表没有使用ToOne或者ToMany建立关系，这时可以用 greendao 的多表查询  queryBuilder.join()  来实现多表查询
 */
class MultiTableMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_table_activity_main)
        findViewById<View>(R.id.button1).setOnClickListener { start(MultiTableGreenDao1Activity::class.java) }
        findViewById<View>(R.id.button2).setOnClickListener { start(MultiTableGreenDao2Activity::class.java) }
    }

    private fun start(c: Class<*>) {
        val intent = Intent(this, c)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
