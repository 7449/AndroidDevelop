package greendao.develop.android.two

import de.greenrobot.daogenerator.DaoGenerator
import de.greenrobot.daogenerator.Schema

object MyGreenDaoGenerator {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        //版本号,包名
        val schema = Schema(1, "greendao.develop.android.two")
        val user = schema.addEntity("User2x")
        user.addIdProperty().primaryKey()
        user.addStringProperty("userName2x").notNull()
        user.addStringProperty("userSex2x").notNull()
        DaoGenerator().generateAll(schema, "./2x/src/main/java")
    }

}
