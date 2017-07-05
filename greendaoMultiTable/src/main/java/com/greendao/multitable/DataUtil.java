package com.greendao.multitable;

import android.widget.Toast;

import com.greendao.multitable.bean.one.ClassBean;
import com.greendao.multitable.bean.one.ClassBeanDao;
import com.greendao.multitable.bean.one.StudentBean;
import com.greendao.multitable.bean.two.SchoolBean;
import com.greendao.multitable.bean.two.SchoolBeanDao;
import com.greendao.multitable.bean.two.TwoClassBean;
import com.greendao.multitable.bean.two.TwoClassBeanDao;

/**
 * 初始化 数据库数据
 */
public class DataUtil {


    public static void initOne() {

        StudentBean xm = new StudentBean(0, "小明", "19", "男");
        ClassBean classBean = new ClassBean(0, xm.getId(), "学前班");
        classBean.setStudentBean(xm);

        StudentBean xh = new StudentBean(1, "小红", "18", "女");
        ClassBean classBean1 = new ClassBean(1, xh.getId(), "一年级");
        classBean1.setStudentBean(xh);

        StudentBean xw = new StudentBean(2, "小王", "19", "男");
        ClassBean classBean2 = new ClassBean(2, xw.getId(), "二年级");
        classBean2.setStudentBean(xw);

        StudentBean atm = new StudentBean(3, "奥特曼", "20", "gay");
        ClassBean classBean3 = new ClassBean(3, atm.getId(), "三年级");
        classBean3.setStudentBean(atm);

        StudentBean hlw = new StudentBean(4, "葫芦娃", "5", "gay");
        ClassBean classBean4 = new ClassBean(4, hlw.getId(), "四年级");
        classBean4.setStudentBean(hlw);

        ClassBeanDao classBeanDao = DBManager.getDaoSession().getClassBeanDao();
        classBeanDao.deleteAll();
        classBeanDao.insertInTx(classBean, classBean1, classBean2, classBean3, classBean4);
//        classBeanDao.insert(classBean);
//        classBeanDao.insert(classBean1);
//        classBeanDao.insert(classBean2);
//        classBeanDao.insert(classBean3);
//        classBeanDao.insert(classBean4);
        Toast.makeText(App.getContext(), "初始化成功", Toast.LENGTH_SHORT).show();
    }


    public static void initTwo() {
        SchoolBean s1 = new SchoolBean(0, "葫芦娃学校");
        TwoClassBean t1 = new TwoClassBean(s1.getId(), 0, "一年级");
        TwoClassBean t2 = new TwoClassBean(s1.getId(), 1, "二年级");
        TwoClassBean t3 = new TwoClassBean(s1.getId(), 2, "三年级");
        TwoClassBean t4 = new TwoClassBean(s1.getId(), 3, "四年级");
        TwoClassBean t5 = new TwoClassBean(s1.getId(), 4, "五年级");
        TwoClassBean t6 = new TwoClassBean(s1.getId(), 5, "六年级");

        SchoolBean s2 = new SchoolBean(1, "奥特曼学校");
        TwoClassBean a1 = new TwoClassBean(s2.getId(), 6, "一");
        TwoClassBean a2 = new TwoClassBean(s2.getId(), 7, "二");
        TwoClassBean a3 = new TwoClassBean(s2.getId(), 8, "三");
        TwoClassBean a4 = new TwoClassBean(s2.getId(), 9, "四");
        TwoClassBean a5 = new TwoClassBean(s2.getId(), 10, "五");
        TwoClassBean a6 = new TwoClassBean(s2.getId(), 11, "六");

        SchoolBean s3 = new SchoolBean(2, "彩虹学校");
        TwoClassBean c1 = new TwoClassBean(s3.getId(), 12, "赤");
        TwoClassBean c2 = new TwoClassBean(s3.getId(), 13, "橙");
        TwoClassBean c3 = new TwoClassBean(s3.getId(), 14, "黄");
        TwoClassBean c4 = new TwoClassBean(s3.getId(), 15, "绿");
        TwoClassBean c5 = new TwoClassBean(s3.getId(), 16, "青");
        TwoClassBean c6 = new TwoClassBean(s3.getId(), 17, "蓝");
        TwoClassBean c7 = new TwoClassBean(s3.getId(), 18, "紫");

        SchoolBean s4 = new SchoolBean(3, "未知学校");
        TwoClassBean w1 = new TwoClassBean(s4.getId(), 19, "未知一");
        TwoClassBean w2 = new TwoClassBean(s4.getId(), 20, "未知二");
        TwoClassBean w3 = new TwoClassBean(s4.getId(), 21, "未知三");
        TwoClassBean w4 = new TwoClassBean(s4.getId(), 22, "未知四");
        TwoClassBean w5 = new TwoClassBean(s4.getId(), 23, "未知五");
        TwoClassBean w6 = new TwoClassBean(s4.getId(), 24, "未知六");

        TwoClassBeanDao twoClassBeanDao = DBManager.getDaoSession().getTwoClassBeanDao();
        twoClassBeanDao.deleteAll();
        SchoolBeanDao schoolBeanDao = DBManager.getDaoSession().getSchoolBeanDao();
        schoolBeanDao.deleteAll();

        twoClassBeanDao.insertInTx(t1, t2, t3, t4, t5, t6, a1, a2, a3, a4, a5, a6, c1, c2, c3, c4, c5, c6, c7, w1, w2, w3, w4, w5, w6);
        schoolBeanDao.insertInTx(s1, s2, s3, s4);
        Toast.makeText(App.getContext(), "初始化成功", Toast.LENGTH_SHORT).show();
    }

}
