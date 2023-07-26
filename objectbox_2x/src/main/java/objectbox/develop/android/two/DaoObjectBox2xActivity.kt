package objectbox.develop.android.two

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import objectbox.develop.android.entity.ObjectBox2xEntity
import objectbox.develop.android.entity.ObjectBox2xEntityDao
import org.greenrobot.daocompat.query.Query

class DaoObjectBox2xActivity : AppCompatActivity() {
    private lateinit var adapter: Version2xAdapter
    private lateinit var objectBoxEntityDao: ObjectBox2xEntityDao
    private lateinit var build: Query<ObjectBox2xEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two_activity_objectbox)

        val dao = ObjectBox2xUtils.dao

        objectBoxEntityDao = dao.objectBox2xEntityDao
        build = objectBoxEntityDao.queryBuilder().build()

        findViewById<View>(R.id.btnStart).setOnClickListener { view ->
            val ls = ArrayList<ObjectBox2xEntity>()
            ls.add(ObjectBox2xEntity(name = "王一", age = 1.toString()))
            ls.add(ObjectBox2xEntity(name = "王二", age = 2.toString()))
            ls.add(ObjectBox2xEntity(name = "王三", age = 3.toString()))
            ls.add(ObjectBox2xEntity(name = "王四", age = 4.toString()))
            ls.add(ObjectBox2xEntity(name = "王五", age = 5.toString()))
            ls.add(ObjectBox2xEntity(name = "王六", age = 6.toString()))
            ls.add(ObjectBox2xEntity(name = "王七", age = 7.toString()))
            ls.add(ObjectBox2xEntity(name = "王八", age = 8.toString()))
            ls.add(ObjectBox2xEntity(name = "王九", age = 9.toString()))
            ls.add(ObjectBox2xEntity(name = "王十", age = 10.toString()))
            objectBoxEntityDao.insertInTx(ls)
            Toast.makeText(view.context, "插入成功", Toast.LENGTH_SHORT).show()
            adapter.addAll(build.find())
        }
        findViewById<View>(R.id.btnDeleted).setOnClickListener { view ->
            objectBoxEntityDao.deleteAll()
            adapter.addAll(build.find())
            Toast.makeText(view.context, "删除成功", Toast.LENGTH_SHORT).show()
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Version2xAdapter(build.find())
        recyclerView.adapter = adapter
    }
}
