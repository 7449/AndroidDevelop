package greendao.develop.android.multi.table.two

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import greendao.develop.android.multi.table.DBManager
import greendao.develop.android.multi.table.DataUtil
import greendao.develop.android.multi.table.R
import greendao.develop.android.multi.table.bean.two.SchoolBeanDao
import kotlinx.android.synthetic.main.multi_table_activity_greendao.*

class MultiTableGreenDao2Activity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_table_activity_greendao)
        title = "一对多"
        btData.setOnClickListener(this)
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
            R.id.btData -> DataUtil.initTwo()
            R.id.btnSearchData -> {
                val where = DBManager.daoSession.schoolBeanDao.queryBuilder()
                    .where(SchoolBeanDao.Properties.Id.eq(etGreendao.text.toString().trim()))
                val list = where.list()

                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "data == null", Toast.LENGTH_SHORT).show()
                    return
                }
                val classBeanList = list[0].classBeen
                if (classBeanList == null || classBeanList.isEmpty()) {
                    Toast.makeText(this, "classBeanList == null", Toast.LENGTH_SHORT).show()
                    return
                }
                var temp = ""
                for (i in classBeanList.indices) {
                    temp += classBeanList[i].className + ","
                }
                AlertDialog.Builder(this)
                    .setMessage("学校名称：" + list[0].schoolName + "\n班级名称：" + temp)
                    .setPositiveButton("ok", null).create().show()
                etGreendao.text?.clear()
            }
        }

    }
}