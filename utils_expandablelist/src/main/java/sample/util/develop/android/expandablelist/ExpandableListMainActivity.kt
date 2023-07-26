package sample.util.develop.android.expandablelist


import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import sample.util.develop.android.expandablelist.widget.ExpandableListDataPump
import sample.util.develop.android.expandablelist.widget.SimpleAdapter

class ExpandableListMainActivity : AppCompatActivity() {
    private val expandableListView by lazy { findViewById<ExpandableListView>(R.id.expandableListView) }
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
