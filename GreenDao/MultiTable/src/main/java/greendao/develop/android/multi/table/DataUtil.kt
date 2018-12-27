package greendao.develop.android.multi.table

import android.widget.Toast
import greendao.develop.android.multi.table.bean.one.ClassBean
import greendao.develop.android.multi.table.bean.one.StudentBean
import greendao.develop.android.multi.table.bean.two.SchoolBean
import greendao.develop.android.multi.table.bean.two.TwoClassBean

/**
 * 初始化 数据库数据
 */
object DataUtil {


    fun initOne() {

        val xm = StudentBean(0, "小明", "19", "男")
        val classBean = ClassBean(0, xm.id, "学前班")
        classBean.studentBean = xm

        val xh = StudentBean(1, "小红", "18", "女")
        val classBean1 = ClassBean(1, xh.id, "一年级")
        classBean1.studentBean = xh

        val xw = StudentBean(2, "小王", "19", "男")
        val classBean2 = ClassBean(2, xw.id, "二年级")
        classBean2.studentBean = xw

        val atm = StudentBean(3, "奥特曼", "20", "gay")
        val classBean3 = ClassBean(3, atm.id, "三年级")
        classBean3.studentBean = atm

        val hlw = StudentBean(4, "葫芦娃", "5", "gay")
        val classBean4 = ClassBean(4, hlw.id, "四年级")
        classBean4.studentBean = hlw

        val classBeanDao = DBManager.daoSession.classBeanDao
        classBeanDao.deleteAll()
        classBeanDao.insertInTx(classBean, classBean1, classBean2, classBean3, classBean4)
        //        classBeanDao.insert(classBean);
        //        classBeanDao.insert(classBean1);
        //        classBeanDao.insert(classBean2);
        //        classBeanDao.insert(classBean3);
        //        classBeanDao.insert(classBean4);
        Toast.makeText(MultiTableApp.context, "初始化成功", Toast.LENGTH_SHORT).show()
    }


    fun initTwo() {
        val s1 = SchoolBean(0, "葫芦娃学校")
        val t1 = TwoClassBean(s1.id, 0, "一年级")
        val t2 = TwoClassBean(s1.id, 1, "二年级")
        val t3 = TwoClassBean(s1.id, 2, "三年级")
        val t4 = TwoClassBean(s1.id, 3, "四年级")
        val t5 = TwoClassBean(s1.id, 4, "五年级")
        val t6 = TwoClassBean(s1.id, 5, "六年级")

        val s2 = SchoolBean(1, "奥特曼学校")
        val a1 = TwoClassBean(s2.id, 6, "一")
        val a2 = TwoClassBean(s2.id, 7, "二")
        val a3 = TwoClassBean(s2.id, 8, "三")
        val a4 = TwoClassBean(s2.id, 9, "四")
        val a5 = TwoClassBean(s2.id, 10, "五")
        val a6 = TwoClassBean(s2.id, 11, "六")

        val s3 = SchoolBean(2, "彩虹学校")
        val c1 = TwoClassBean(s3.id, 12, "赤")
        val c2 = TwoClassBean(s3.id, 13, "橙")
        val c3 = TwoClassBean(s3.id, 14, "黄")
        val c4 = TwoClassBean(s3.id, 15, "绿")
        val c5 = TwoClassBean(s3.id, 16, "青")
        val c6 = TwoClassBean(s3.id, 17, "蓝")
        val c7 = TwoClassBean(s3.id, 18, "紫")

        val s4 = SchoolBean(3, "未知学校")
        val w1 = TwoClassBean(s4.id, 19, "未知一")
        val w2 = TwoClassBean(s4.id, 20, "未知二")
        val w3 = TwoClassBean(s4.id, 21, "未知三")
        val w4 = TwoClassBean(s4.id, 22, "未知四")
        val w5 = TwoClassBean(s4.id, 23, "未知五")
        val w6 = TwoClassBean(s4.id, 24, "未知六")

        val twoClassBeanDao = DBManager.daoSession.twoClassBeanDao
        twoClassBeanDao.deleteAll()
        val schoolBeanDao = DBManager.daoSession.schoolBeanDao
        schoolBeanDao.deleteAll()

        twoClassBeanDao.insertInTx(
            t1,
            t2,
            t3,
            t4,
            t5,
            t6,
            a1,
            a2,
            a3,
            a4,
            a5,
            a6,
            c1,
            c2,
            c3,
            c4,
            c5,
            c6,
            c7,
            w1,
            w2,
            w3,
            w4,
            w5,
            w6
        )
        schoolBeanDao.insertInTx(s1, s2, s3, s4)
        Toast.makeText(MultiTableApp.context, "初始化成功", Toast.LENGTH_SHORT).show()
    }

}
