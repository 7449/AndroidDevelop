package greendao.develop.android.multi.table


import greendao.develop.android.multi.table.bean.one.DaoMaster
import greendao.develop.android.multi.table.bean.one.DaoSession

object DBManager {

    private const val SQL_NAME = "multi_table_sample"

    val daoSession: DaoSession =
        DaoMaster(
            DaoMaster.DevOpenHelper(
                MultiTableApp.context,
                SQL_NAME,
                null
            ).writableDatabase
        ).newSession()
}
