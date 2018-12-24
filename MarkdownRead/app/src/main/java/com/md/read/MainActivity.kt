package com.md.read

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.tiagohm.markdownview.css.styles.Github
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnItemClickListener
import com.xadapter.listener.OnXBindListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_markdown.*

class Entity(val path: String, val name: String)

class MainActivity : AppCompatActivity(), OnXBindListener<Entity>, OnItemClickListener<Entity> {

    override fun onXBind(holder: XViewHolder, position: Int, entity: Entity) {
        holder.setTextView(R.id.tv_name, entity.name)
    }

    override fun onItemClick(view: View, position: Int, entity: Entity) {
        val bundle = Bundle()
        bundle.putString("name", entity.name)
        startActivity(Intent(this, MarkdownActivity().javaClass).putExtras(bundle))
    }

    private lateinit var xRecyclerViewAdapter: XRecyclerViewAdapter<Entity>
    private val suffix: String = ".md"
    private val entityList = ArrayList<Entity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        xRecyclerViewAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = xRecyclerViewAdapter.apply {
            dataContainer = entityList
            onXBindListener = this@MainActivity
            onItemClickListener = this@MainActivity
            itemLayoutId = R.layout.item_entity
        }
        assetsFile("")
        xRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun assetsFile(key: String) {
        val list = assets.list(key) ?: return
        list.forEach { element ->
            run {
                if (!element.contains(".")) {
                    assetsFile(element)
                }
                if (element.endsWith(suffix)) {
                    entityList.add(Entity("", element))
                }
            }
        }
    }
}


class MarkdownActivity : AppCompatActivity() {
    private val mStyle = Github()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_markdown)
        val name = intent.extras?.getString("name", "name")
        title = name
        markdown.addStyleSheet(mStyle)
        markdown.loadMarkdownFromAsset(name)
    }
}
