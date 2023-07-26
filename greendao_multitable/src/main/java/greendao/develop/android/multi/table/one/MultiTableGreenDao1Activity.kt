package greendao.develop.android.multi.table.one

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import greendao.develop.android.multi.table.DBManager
import greendao.develop.android.multi.table.DataUtil
import greendao.develop.android.multi.table.R
import greendao.develop.android.multi.table.bean.one.ClassBeanDao

/**
 * 简单使用了greendao 链表 一对一的情况下 查询数据
 */

class MultiTableGreenDao1Activity : AppCompatActivity(), View.OnClickListener {

    private val etGreendao by lazy { findViewById<EditText>(R.id.etGreendao) }
    private val btnSearchData by lazy { findViewById<View>(R.id.btnSearchData) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_table_activity_greendao)
        title = "一对一"
        findViewById<View>(R.id.btData).setOnClickListener(this)
        btnSearchData.setOnClickListener(this)
        etGreendao.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btnSearchData.isEnabled = s.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btData -> DataUtil.initOne()
            R.id.btnSearchData -> {
                val queryBuilder = DBManager.daoSession.classBeanDao.queryBuilder()
                    .where(ClassBeanDao.Properties.StudentId.eq(etGreendao.text.toString().trim()))
                val list = queryBuilder.list()

                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "data == null", Toast.LENGTH_SHORT).show()
                    return
                }
                val studentBean = list[0].studentBean
                if (studentBean == null) {
                    Toast.makeText(this, "studentBean == null", Toast.LENGTH_SHORT).show()
                    return
                }
                AlertDialog.Builder(this)
                    .setMessage("年级：" + list[0].className + "\n姓名：" + studentBean.name + "\n性别：" + studentBean.sex + "\n年龄：" + studentBean.age)
                    .setPositiveButton("ok", null).create().show()
                etGreendao.text?.clear()
            }
        }
    }
}
