package github.com.greendao.sql;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * by y on 2016/10/8
 */

public class MyGreenDaoGenerator {

    public static void main(String[] args) throws Exception {
        //版本号,包名
        Schema schema = new Schema(1, "github.com.greendao.sql");
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey();
        user.addStringProperty("userName").notNull();
        user.addStringProperty("userSex").notNull();
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

}
