package greendao.develop.android.external

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.external_activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * greenDaoExternal:用greenDao读取本地数据库里面的表
 *
 *
 * android数据库操作都是在databases文件夹下，所以要把需要动的数据库复制到databases文件夹下
 *
 *
 * 需要注意的是greenDao自动生成的时候
 * table 默认为TABLENAME
 * 默认id生成时"_id"
 * 这些生成的字段必须要和数据表中的字段一致，可以用 nameInDb="" 指定字段name和tableName
 */
@Suppress("UseExpressionBody")
class ExternalMainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ExternalMainActivity"
        @SuppressLint("SdCardPath")
        private const val DB_PATH = "/data/data/android.develop.greendao/databases/"
        private const val DB_NAME = "external.db"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.external_activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        copyDBToDatabases()
        recyclerView.adapter = ExternalMainAdapter(
            DaoMaster(DaoMaster.DevOpenHelper(this, DB_NAME, null).writableDatabase)
                .newSession()
                .externalBeanDao
                .loadAll()
        )
    }

    private fun copyDBToDatabases() {
        try {
            val outFileName = DB_PATH + DB_NAME
            val file = File(DB_PATH)
            if (!file.mkdirs()) {
                file.mkdirs()
            }
            val dataFile = File(outFileName)
            if (dataFile.exists()) {
                dataFile.delete()
            }
            val myInput: InputStream = applicationContext.assets.open(DB_NAME)
            val myOutput = FileOutputStream(outFileName)
            val buffer = ByteArray(1024)
            var length: Int
            while (myInput.read(buffer).apply { length = this } != -1) {
                myOutput.write(buffer, 0, length)
            }
            myOutput.flush()
            myOutput.close()
            myInput.close()
        } catch (e: IOException) {
            Log.i(TAG, "error--->" + e.toString())
            e.printStackTrace()
        }

    }


}
