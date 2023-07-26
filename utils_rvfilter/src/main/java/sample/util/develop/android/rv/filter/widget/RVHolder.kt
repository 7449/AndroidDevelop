package sample.util.develop.android.rv.filter.widget

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewSparseArray: SparseArray<View> = SparseArray()

    init {
        itemView.tag = viewSparseArray
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : View> get(id: Int): T {
        var childView: View? = viewSparseArray.get(id)
        if (childView == null) {
            childView = itemView.findViewById(id)
            viewSparseArray.put(id, childView)
        }
        return childView as T
    }

    fun getTextView(id: Int): TextView {
        return get(id)
    }

    fun getImageView(id: Int): ImageView {
        return get(id)
    }

    fun setTextView(id: Int, charSequence: CharSequence) {
        getTextView(id).text = charSequence
    }

    fun setTextColor(id: Int, color: Int) {
        getTextView(id).setTextColor(color)
    }

}
