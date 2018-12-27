package objectbox.develop.android.multitable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.multi_tablea_ctivity_main.*
import objectbox.develop.android.entity.SchoolEntity
import objectbox.develop.android.entity.StudentEntity

class MultiTableMainActivity : AppCompatActivity() {

    private lateinit var schoolEntityBox: Box<SchoolEntity>
    private var init = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_tablea_ctivity_main)
        val boxStore = MultiTableObjectBoxUtils.boxStore
        schoolEntityBox = boxStore.boxFor()

        btnMultiTable.setOnClickListener { initDb() }
        btnQuery.setOnClickListener(View.OnClickListener { view ->
            val trim = etId.text.toString().trim()
            if (trim.isEmpty() || !init) {
                return@OnClickListener
            }
            if (trim.toLong() != 1L && trim.toLong() != 2L) {
                return@OnClickListener
            }
            val schoolEntity = schoolEntityBox.get(trim.toLong()) ?: return@OnClickListener
            var temp = "school关联的表Size：" + schoolEntity.student.size
            val student = schoolEntity.student
            for (studentEntity in student) {
                temp += "\n" + studentEntity.name + " "
            }
            Toast.makeText(view.context, temp, Toast.LENGTH_SHORT).show()
            etId.text?.clear()
        })
        btnQuery.isEnabled = false
        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                btnQuery.isEnabled = charSequence.isNotEmpty()
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

    private fun initDb() {
        schoolEntityBox.removeAll()

        val list1 = ArrayList<StudentEntity>()
        val studentEntity1 = StudentEntity()
        studentEntity1.name = "1-->a"
        val studentEntity2 = StudentEntity()
        studentEntity2.name = "1-->b"
        val studentEntity3 = StudentEntity()
        studentEntity3.name = "1-->c"
        val studentEntity4 = StudentEntity()
        studentEntity4.name = "1-->d"
        list1.add(studentEntity1)
        list1.add(studentEntity2)
        list1.add(studentEntity3)
        list1.add(studentEntity4)
        val schoolEntity1 = SchoolEntity()
        schoolEntity1.student.addAll(list1)
        schoolEntity1.id = 1

        val list2 = ArrayList<StudentEntity>()
        val studentEntity5 = StudentEntity()
        studentEntity5.name = "2-->a"
        val studentEntity6 = StudentEntity()
        studentEntity6.name = "2-->b"
        val studentEntity7 = StudentEntity()
        studentEntity7.name = "2-->c"
        list2.add(studentEntity5)
        list2.add(studentEntity6)
        list2.add(studentEntity7)
        val schoolEntity2 = SchoolEntity()
        schoolEntity2.student.addAll(list2)
        schoolEntity2.id = 2

        Toast.makeText(this, "初始化成功", Toast.LENGTH_SHORT).show()
        schoolEntityBox.put(schoolEntity1, schoolEntity2)
        init = true
    }

}
