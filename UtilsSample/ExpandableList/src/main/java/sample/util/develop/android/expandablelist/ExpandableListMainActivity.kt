package sample.util.develop.android.expandablelist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.expandable_list_activity_main.*
import sample.util.develop.android.expandablelist.widget.ExpandableListDataPump
import sample.util.develop.android.expandablelist.widget.SimpleAdapter


import java.util.ArrayList

class ExpandableListMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expandable_list_activity_main)
        val itemData = ExpandableListDataPump.data
        val title = ArrayList<String>(itemData.keys)
        val adapter = SimpleAdapter(title, itemData)
        expandableListView.setGroupIndicator(null)
        expandableListView.setAdapter(adapter)
    }
}
