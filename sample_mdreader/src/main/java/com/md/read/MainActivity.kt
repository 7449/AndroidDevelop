package com.md.read

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.addAll
import com.xadapter.holder.setText
import com.xadapter.setItemLayoutId
import com.xadapter.setOnBind
import com.xadapter.setOnItemClickListener

class MainActivity : AppCompatActivity() {

    private val entityList = ArrayList<Entity>()
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val xRecyclerViewAdapter = XRecyclerViewAdapter<Entity>()
        recyclerView.adapter = xRecyclerViewAdapter
        xRecyclerViewAdapter
            .setItemLayoutId(R.layout.item_entity)
            .setOnBind { holder, _, entity -> holder.setText(R.id.tv_name, entity.name) }
            .setOnItemClickListener { _, _, entity ->
                startActivity(Intent(this, MarkdownActivity().javaClass).putExtras(Bundle().apply {
                    putString(NAME, entity.name)
                    putString(FINDER, entity.finderName)
                }))
            }
        assetsFile("")
        xRecyclerViewAdapter.addAll(entityList)
    }

    private fun assetsFile(key: String) {
        val list = assets.list(key) ?: return
        list.forEach { element ->
            if (element.endsWith(MD_SUFFIX)) {
                entityList.add(Entity("", key, element))
            } else {
                assetsFile(element)
            }
        }
    }
}
