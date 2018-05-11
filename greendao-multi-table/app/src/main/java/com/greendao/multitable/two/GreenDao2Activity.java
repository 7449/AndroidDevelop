package com.greendao.multitable.two;

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
import com.greendao.multitable.bean.two.SchoolBean;
import com.greendao.multitable.bean.two.SchoolBeanDao;
import com.greendao.multitable.bean.two.TwoClassBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * by y on 05/07/2017.
 */

public class GreenDao2Activity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText editText;
    private AppCompatButton searchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        setTitle("一对多");
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
                DataUtil.initTwo();
                break;
            case R.id.btn_search_data:
                QueryBuilder<SchoolBean> where = DBManager.getDaoSession().getSchoolBeanDao().queryBuilder().where(SchoolBeanDao.Properties.Id.eq(editText.getText().toString().trim()));
                List<SchoolBean> list = where.list();

                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "data == null", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TwoClassBean> classBeanList = list.get(0).getClassBeen();
                if (classBeanList == null || classBeanList.isEmpty()) {
                    Toast.makeText(this, "classBeanList == null", Toast.LENGTH_SHORT).show();
                    return;
                }
                String temp = "";
                for (int i = 0; i < classBeanList.size(); i++) {
                    temp += classBeanList.get(i).getClassName() + ",";
                }
                new AlertDialog
                        .Builder(this)
                        .setMessage("学校名称：" + list.get(0).getSchoolName() + "\n班级名称：" + temp)
                        .setPositiveButton("ok", null).create().show();
                editText.getText().clear();
                break;
        }

    }
}