package greendao.develop.android.three

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Version3XMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var writableDatabase: SQLiteDatabase
    private lateinit var userDao: User3xDao
    private lateinit var simpleCursorAdapter: SimpleCursorAdapter

    private val add by lazy { findViewById<View>(R.id.add) }
    private val delete by lazy { findViewById<View>(R.id.delete) }
    private val update by lazy { findViewById<View>(R.id.update) }
    private val search by lazy { findViewById<View>(R.id.search) }
    private val list by lazy { findViewById<ListView>(R.id.list) }

    private val userName by lazy { findViewById<EditText>(R.id.userName) }
    private val userSex by lazy { findViewById<EditText>(R.id.userSex) }
    private val etDelete by lazy { findViewById<EditText>(R.id.etDelete) }
    private val etUpDateId by lazy { findViewById<EditText>(R.id.etUpDateId) }
    private val etUpDateName by lazy { findViewById<EditText>(R.id.etUpDateName) }
    private val etUpDateSex by lazy { findViewById<EditText>(R.id.etUpDateSex) }
    private val etSearch by lazy { findViewById<EditText>(R.id.etSearch) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.version_3x_activity_main)

        add.setOnClickListener(this)
        delete.setOnClickListener(this)
        update.setOnClickListener(this)
        search.setOnClickListener(this)

        init()
    }

    private fun init() {
        writableDatabase = DaoMaster.DevOpenHelper(this, "greendao3x", null).writableDatabase
        val daoSession = DaoMaster(writableDatabase).newSession()
        userDao = daoSession.user3xDao
        val cursor = writableDatabase.query(
            userDao.tablename,
            userDao.allColumns,
            null,
            null,
            null,
            null,
            null
        )
        val from =
            arrayOf(User3xDao.Properties.Name3x.columnName, User3xDao.Properties.Age3x.columnName)
        val id = intArrayOf(android.R.id.text1, android.R.id.text2)
        simpleCursorAdapter =
            SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                from,
                id,
                Adapter.NO_SELECTION
            )
        list.adapter = simpleCursorAdapter
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.add) {
            if (userName.text.isNotEmpty() && userSex.text.isNotEmpty()) {
                addSQLite(userName.text.toString(), userSex.text.toString())
                userName.text.clear()
                userSex.text.clear()
            } else {
                Toast.makeText(applicationContext, "name sex must not null", Toast.LENGTH_LONG)
                    .show()
            }

        } else if (i == R.id.delete) {
            if (etDelete.text.isNotEmpty()) {
                deleteSQLite(etDelete.text.toString().trim().toLong())
                etDelete.text.clear()
            } else {
                Toast.makeText(applicationContext, "id illegal", Toast.LENGTH_LONG).show()
            }

        } else if (i == R.id.update) {
            if (etUpDateId.text.isNotEmpty() && etUpDateName.text.isNotEmpty() && etUpDateSex.text.isNotEmpty()) {
                upDateSQLite(
                    etUpDateId.text.toString().trim().toLong(),
                    etUpDateName.text.toString().trim(),
                    etUpDateSex.text.toString().trim()
                )
                etUpDateId.text.clear()
                etUpDateName.text.clear()
                etUpDateSex.text.clear()
            } else {
                Toast.makeText(applicationContext, "id name sex must not null", Toast.LENGTH_LONG)
                    .show()
            }

        } else if (i == R.id.search) {
            if (etSearch.text.isNotEmpty()) {
                searchSQLite(etSearch.text.toString().trim().toLong())
                etSearch.text.clear()
            } else {
                Toast.makeText(applicationContext, "id illegal", Toast.LENGTH_LONG).show()
            }

        }
        val cursor = writableDatabase.query(
            userDao.tablename,
            userDao.allColumns,
            null,
            null,
            null,
            null,
            null
        )
        simpleCursorAdapter.swapCursor(cursor)
    }

    //add sql data
    private fun addSQLite(name: String, sex: String) {

        val user = User3x(null, name, sex)

        userDao.insert(user)
    }


    //delete sql data
    private fun deleteSQLite(id: Long?) {
        userDao.deleteByKey(id)
        //delete sql all;
        //        userDao.deleteAll();
    }


    //update sql data
    private fun upDateSQLite(id: Long, name: String, sex: String) {
        userDao.update(User3x(id, name, sex))
    }

    //search sql data
    private fun searchSQLite(id: Long) {
        val queryBuilder = userDao.queryBuilder().where(User3xDao.Properties.Id.eq(id))
        // .list() Returns a collection of entity classes
        val user = queryBuilder.list()
        // If you only want results , use .unique() method
        // Person person = queryBuilder.unique();
        AlertDialog.Builder(this)
            .setMessage(if (user != null && user.size > 0) user[0].name3x + "--" + user[0].age3x else "data null")
            .setPositiveButton("ok", null).create().show()
    }
}
