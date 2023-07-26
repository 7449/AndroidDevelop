package sample.util.develop.android.expandablelist.widget

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter

abstract class SimpleBaseExpandableListAdapter<T>(
    protected var expandableListTitle: List<T>,
    private var expandableListDetail: HashMap<T, List<T>>
) : BaseExpandableListAdapter() {


    override fun getGroupCount(): Int = expandableListTitle.size

    override fun getChildrenCount(groupPosition: Int): Int =
        expandableListDetail[expandableListTitle[groupPosition]]?.size ?: 0

    override fun getGroup(groupPosition: Int): T = expandableListTitle[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): T =
        expandableListDetail[expandableListTitle[groupPosition]]!![childPosition]!!

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View =
        getGroup(groupPosition, isExpanded, convertView, parent)

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View = getChild(groupPosition, childPosition, isLastChild, convertView, parent)

    abstract fun getGroup(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View

    abstract fun getChild(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}
