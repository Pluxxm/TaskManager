package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectGroupActivity extends AppCompatActivity {

    private List<CheckBox> checkBoxes;
    private Button btn_selectGroup;
    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectgroup);

        initCheckBoxes();
        initView();
        addList();
    }

    private void addList() {
        //添加选择的清单名称到mList 传到下一个activity
        for (int i = 0; i < checkBoxes.size(); i++){
            checkBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        mList.add((String) compoundButton.getText());
                        Log.d("Check", compoundButton.getText()+"");
                    }else{
                        mList.remove((String) compoundButton.getText());
                    }
                }
            });
        }
    }

    private void initView() {
        btn_selectGroup = (Button)findViewById(R.id.btn_selectgroup);
        btn_selectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mList.size() > 0){
                    Intent intent = new Intent(SelectGroupActivity.this,MainActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putStringArrayList("SelectList", (ArrayList<String>) mList);
//                    intent.putExtras(bundle);
                    DataUtil.dataUtilInstance.setGroup(mList);
                    startActivity(intent);
                }else{
                    Toast.makeText(SelectGroupActivity.this,"请选择至少一个任务组",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initCheckBoxes() {
        //选择初始清单
        checkBoxes = new ArrayList<>();
        mList = new ArrayList<String>();
        CheckBox checkBox1 = (CheckBox)findViewById(R.id.group_movie);
        CheckBox checkBox2 = (CheckBox)findViewById(R.id.group_family);
        CheckBox checkBox3 = (CheckBox)findViewById(R.id.group_private);
        CheckBox checkBox4 = (CheckBox)findViewById(R.id.group_shop);
        CheckBox checkBox5 = (CheckBox)findViewById(R.id.group_travel);
        CheckBox checkBox6 = (CheckBox)findViewById(R.id.group_work);
        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        checkBoxes.add(checkBox4);
        checkBoxes.add(checkBox5);
        checkBoxes.add(checkBox6);

        Log.d("Check",checkBoxes.size()+"");
    }
}
