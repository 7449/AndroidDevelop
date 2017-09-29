package com.greendao.multitable.bean.test;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * by y on 05/07/2017.
 */


@Entity
public class SimpleSchoolBean {
    @Id
    private long id;

    @ToMany
    @JoinEntity(
            entity = SimpleJoinBean.class,
            sourceProperty = "schoolBeanId",
            targetProperty = "classBeanId"
    )
    private List<SimpleClassBean> classBeen;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 94108780)
    private transient SimpleSchoolBeanDao myDao;

    @Generated(hash = 431951194)
    public SimpleSchoolBean(long id) {
        this.id = id;
    }

    @Generated(hash = 1681296826)
    public SimpleSchoolBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 720726642)
    public List<SimpleClassBean> getClassBeen() {
        if (classBeen == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SimpleClassBeanDao targetDao = daoSession.getSimpleClassBeanDao();
            List<SimpleClassBean> classBeenNew = targetDao
                    ._querySimpleSchoolBean_ClassBeen(id);
            synchronized (this) {
                if (classBeen == null) {
                    classBeen = classBeenNew;
                }
            }
        }
        return classBeen;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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
    @Generated(hash = 1514215549)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSimpleSchoolBeanDao() : null;
    }
}