package objectbox.develop.android.two

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import kotlinx.android.synthetic.main.two_activity_objectbox.*
import objectbox.develop.android.entity.ObjectBox2xEntity

class ObjectBox2xActivity : AppCompatActivity() {
    private lateinit var adapter: Version2xAdapter
    private lateinit var objectBoxEntityBox: Box<ObjectBox2xEntity>
    private lateinit var build: Query<ObjectBox2xEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two_activity_objectbox)
        val boxStore = ObjectBox2xUtils.boxStore
        objectBoxEntityBox = boxStore.boxFor()
        build = objectBoxEntityBox.query().build()
        btnStart.setOnClickListener { view ->
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
            objectBoxEntityBox.put(ls)
            Toast.makeText(view.context, "插入成功", Toast.LENGTH_SHORT).show()
            adapter.addAll(build.find())
        }
        btnDeleted.setOnClickListener { view ->
            objectBoxEntityBox.removeAll()
            adapter.addAll(build.find())
            Toast.makeText(view.context, "删除成功", Toast.LENGTH_SHORT).show()
        }
        btnUpdate.setOnClickListener { v ->
            val ls = ArrayList<ObjectBox2xEntity>()
            ls.add(ObjectBox2xEntity(name = "王八", age = 8.toString()))
            ls.add(ObjectBox2xEntity(name = "王九", age = 9.toString()))
            ls.add(ObjectBox2xEntity(name = "王十", age = 10.toString()))
            objectBoxEntityBox.put(ls)
            Toast.makeText(v.context, "插入成功", Toast.LENGTH_SHORT).show()
            adapter.addAll(build.find())
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Version2xAdapter(objectBoxEntityBox.all)
        recyclerView.adapter = adapter
    }
}
