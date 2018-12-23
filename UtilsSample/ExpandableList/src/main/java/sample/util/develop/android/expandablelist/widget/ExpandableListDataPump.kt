package sample.util.develop.android.expandablelist.widget

import java.util.ArrayList
import java.util.LinkedHashMap

object ExpandableListDataPump {
    val data: LinkedHashMap<String, List<String>>
        get() {
            val expandableListDetail = LinkedHashMap<String, List<String>>()
            val technology = ArrayList<String>()
            technology.add("A")
            technology.add("B")
            technology.add("C")
            technology.add("D")
            technology.add("E")
            technology.add("F")
            technology.add("G")
            technology.add("H")
            technology.add("I")
            technology.add("J")
            val entertainment = ArrayList<String>()
            entertainment.add("A")
            entertainment.add("B")
            entertainment.add("C")
            entertainment.add("D")
            entertainment.add("E")
            entertainment.add("F")
            entertainment.add("G")
            entertainment.add("H")
            entertainment.add("I")
            entertainment.add("J")
            val science = ArrayList<String>()
            science.add("A")
            science.add("B")
            science.add("C")
            science.add("D")
            science.add("E")
            science.add("F")
            science.add("G")
            science.add("H")
            science.add("I")
            science.add("J")
            expandableListDetail["SIMPLE ONE"] = technology
            expandableListDetail["SIMPLE TWO"] = entertainment
            expandableListDetail["SIMPLE THREE"] = science
            return expandableListDetail
        }
}
