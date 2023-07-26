package sample.util.develop.android.expandablelist.widget


import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import sample.util.develop.android.expandablelist.R

class SimpleAdapter(
    expandableListTitle: List<String>,
    expandableListDetail: HashMap<String, List<String>>
) :
    SimpleBaseExpandableListAdapter<String>(expandableListTitle, expandableListDetail) {

    override fun getGroup(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val rootView = View.inflate(parent.context, R.layout.expandable_list_group_item, null)
        val data = expandableListTitle[groupPosition]
        val textView = rootView.findViewById<TextView>(R.id.tvGroup)
        val imageView = rootView.findViewById<ImageView>(R.id.iv)
        textView.text = data
        if (isExpanded) {
            imageView.setImageResource(R.drawable.dropdown)
        } else {
            imageView.setImageResource(R.drawable.select)
        }
        return rootView
    }

    override fun getChild(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val rootView = View.inflate(parent.context, R.layout.expandable_list_child_item, null)
        val tv = rootView.findViewById<TextView>(R.id.tvChild)
        tv.text = getChild(groupPosition, childPosition)
        return rootView
    }
}
