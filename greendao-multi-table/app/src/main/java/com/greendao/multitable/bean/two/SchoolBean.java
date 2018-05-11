package com.greendao.multitable.bean.two;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.greendao.multitable.bean.one.DaoSession;

/**
 * by y on 05/07/2017.
 * <p>
 * <p>
 * {@link ToMany}
 * <p>
 * *     referencedJoinProperty:   目标实体中需要一个与当前实体关联的外键，然后在当前实体中将 外键 作为 referencedJoinProperty 的属性
 * <p>
 * *     joinProperties：自定义属性外键，name 为 当前实体中的 关联键， referencedName 为 目标实体中的外键
 * <p>
 * <p>
 * {@link org.greenrobot.greendao.annotation.JoinEntity}{
 * *    see{@link com.greendao.multitable.bean.one.ClassBean}
 * *    see{@link com.greendao.multitable.bean.one.StudentBean}
 * <p>
 * *   Class<?> entity(): 引用类，保存目标实体和当前实体的属性
 * <p>
 * <p>
 * *    String sourceProperty(): 连接实体中具有当前实体id的属性的名称
 * <p>
 * <p>
 * *     String targetProperty(): 持有目标实体id的连接实体中的属性名称
 * <p>
 * <p>
 * }
 */

@Entity
public class SchoolBean {

    @Id
    private long id;

    @NotNull
    private String schoolName;

//    @ToMany(referencedJoinProperty = "classId")
//    private List<TwoClassBean> classBeen;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "classId")
    })
    private List<TwoClassBean> classBeen;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 411127457)
    private transient SchoolBeanDao myDao;

    @Generated(hash = 1827081441)
    public SchoolBean(long id, @NotNull String schoolName) {
        this.id = id;
        this.schoolName = schoolName;
    }

    @Generated(hash = 2040299565)
    public SchoolBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 86404637)
    public List<TwoClassBean> getClassBeen() {
        if (classBeen == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TwoClassBeanDao targetDao = daoSession.getTwoClassBeanDao();
            List<TwoClassBean> classBeenNew = targetDao
                    ._querySchoolBean_ClassBeen(id);
            synchronized (this) {
                if (classBeen == null) {
                    classBeen = classBeenNew;
                }
            }
        }
        return classBeen;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 2035428468)
    public synchronized void resetClassBeen() {
        classBeen = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1561428149)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSchoolBeanDao() : null;
    }

}
