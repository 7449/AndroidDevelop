package com.objectbox.multitable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.relation.ToMany;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton query;
    private Box<SchoolEntity> schoolEntityBox;
    private AppCompatEditText appCompatEditText;

    boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BoxStore boxStore = ObjectBoxUtils.getBoxStore();
        schoolEntityBox = boxStore.boxFor(SchoolEntity.class);
        appCompatEditText = (AppCompatEditText) findViewById(R.id.et_id);

        findViewById(R.id.btn_multi_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDb();
            }
        });
        query = (AppCompatButton) findViewById(R.id.btn_query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = appCompatEditText.getText().toString().trim();
                if (TextUtils.isEmpty(trim) || !init) {
                    return;
                }
                if (Long.parseLong(trim) != 1 && Long.parseLong(trim) != 2) {
                    return;
                }
                SchoolEntity schoolEntity = schoolEntityBox.get(Long.parseLong(trim));
                String temp = "school关联的表Size：" + schoolEntity.student.size();
                ToMany<StudentEntity> student = schoolEntity.student;
                for (StudentEntity studentEntity : student) {
                    temp += "\n" + studentEntity.name + " ";
                }
                Toast.makeText(view.getContext(), temp, Toast.LENGTH_SHORT).show();
                appCompatEditText.getText().clear();
            }
        });
        query.setEnabled(false);
        appCompatEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                query.setEnabled(charSequence.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initDb() {
        schoolEntityBox.removeAll();

        SchoolEntity schoolEntity1 = new SchoolEntity();
        List<StudentEntity> list1 = new ArrayList<>();
        StudentEntity studentEntity1 = new StudentEntity();
        studentEntity1.name = "1-->a";
        StudentEntity studentEntity2 = new StudentEntity();
        studentEntity2.name = "1-->b";
        StudentEntity studentEntity3 = new StudentEntity();
        studentEntity3.name = "1-->c";
        StudentEntity studentEntity4 = new StudentEntity();
        studentEntity4.name = "1-->d";
        list1.add(studentEntity1);
        list1.add(studentEntity2);
        list1.add(studentEntity3);
        list1.add(studentEntity4);
        schoolEntity1.student.addAll(list1);

        SchoolEntity schoolEntity2 = new SchoolEntity();
        List<StudentEntity> list2 = new ArrayList<>();
        StudentEntity studentEntity5 = new StudentEntity();
        studentEntity5.name = "2-->a";
        StudentEntity studentEntity6 = new StudentEntity();
        studentEntity6.name = "2-->b";
        StudentEntity studentEntity7 = new StudentEntity();
        studentEntity7.name = "2-->c";
        list2.add(studentEntity5);
        list2.add(studentEntity6);
        list2.add(studentEntity7);
        schoolEntity2.student.addAll(list2);

        Toast.makeText(this, "初始化成功", Toast.LENGTH_SHORT).show();
        schoolEntityBox.put(schoolEntity1, schoolEntity2);
        init = true;
    }

}
