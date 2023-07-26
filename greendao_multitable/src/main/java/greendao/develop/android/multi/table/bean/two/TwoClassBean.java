package greendao.develop.android.multi.table.bean.two;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * by y on 05/07/2017.
 */
@Entity
public class TwoClassBean {

    private long classId;

    @Id
    private long id;

    @NotNull
    private String className;

    @Generated(hash = 1183900464)
    public TwoClassBean(long classId, long id, @NotNull String className) {
        this.classId = classId;
        this.id = id;
        this.className = className;
    }

    @Generated(hash = 1711513281)
    public TwoClassBean() {
    }

    public long getClassId() {
        return this.classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
