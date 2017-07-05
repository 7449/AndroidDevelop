package com.greendao.multitable.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.greendao.multitable.DBManager;
import com.greendao.multitable.DataUtil;
import com.greendao.multitable.R;
import com.greendao.multitable.bean.one.ClassBean;
import com.greendao.multitable.bean.one.ClassBeanDao;
import com.greendao.multitable.bean.one.StudentBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * by y on 05/07/2017.
 * <p>
 * <p>
 * 简单使用了greendao 链表 一对一的情况下 查询数据
 */

public class GreenDao1Activity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText editText;
    private AppCompatButton searchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        setTitle("一对一");
        findViewById(R.id.bt_data).setOnClickListener(this);
        searchButton = (AppCompatButton) findViewById(R.id.btn_search_data);
        searchButton.setOnClickListener(this);
        editText = (AppCompatEditText) findViewById(R.id.et_greendao);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchButton.setEnabled(s.length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_data:
                DataUtil.initOne();
                break;
            case R.id.btn_search_data:
                QueryBuilder<ClassBean> queryBuilder = DBManager.getDaoSession().getClassBeanDao().queryBuilder().where(ClassBeanDao.Properties.StudentId.eq(editText.getText().toString().trim()));
                List<ClassBean> list = queryBuilder.list();

                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "data == null", Toast.LENGTH_SHORT).show();
                    return;
                }
                StudentBean studentBean = list.get(0).getStudentBean();
                if (studentBean == null) {
                    Toast.makeText(this, "studentBean == null", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog
                        .Builder(this)
                        .setMessage("年级：" + list.get(0).getClassName() + "\n姓名：" + studentBean.getName() + "\n性别：" + studentBean.getSex() + "\n年龄：" + studentBean.getAge())
                        .setPositiveButton("ok", null).create().show();
                editText.getText().clear();
                break;
        }
    }
}
