package com.example.taskmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.adapter.AddGroupRectclerviewAdapter;
import com.example.taskmanager.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

//添加新清单页面 填写清单名称 包括添加团队成员
public class AddGroupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText et_groupname;
    private RecyclerView rv_listMembers;
    private List<UserBean> mList; //添加成员的list
    private AddGroupRectclerviewAdapter addGroupRectclerviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgroup);

        initList();
        initToolbar();
        initView();
    }

    //初始化邀请用户的默认列表 包含所有者与添加朋友按钮条
    //邀请新的朋友插入到列表的中间
    private void initList() {
        mList = new ArrayList<>();
        UserBean owner = DataUtil.dataUtilInstance.getLoginer();
        mList.add(new UserBean(owner.getAvatar(),owner.getName()));
        mList.add(new UserBean(R.drawable.icon_add_member,"邀请朋友"));
    }

    private void initView() {
        et_groupname = findViewById(R.id.et_groupname);
        rv_listMembers = (RecyclerView)findViewById(R.id.rv_listmembers);
        rv_listMembers.setLayoutManager(new LinearLayoutManager(this));
        rv_listMembers.setItemAnimator(new DefaultItemAnimator());

        addGroupRectclerviewAdapter = new AddGroupRectclerviewAdapter(this, mList);
        rv_listMembers.addItemDecoration(new DividerItemDecoration(AddGroupActivity.this
                , DividerItemDecoration.VERTICAL_LIST, 0));
        rv_listMembers.setAdapter(addGroupRectclerviewAdapter);
        addGroupRectclerviewAdapter.setOnItemClickListener(new AddGroupRectclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击事件 只有最后一条具有点击事件转到搜索用户界面
                if(position == mList.size() - 1) {
                    Intent intent = new Intent(AddGroupActivity.this, AddFriendsActivity.class);
                    startActivity(intent);
                }
                Toast.makeText(AddGroupActivity.this
                        ,"点击了第"+position+"条，一共有"+mList.size()+"条",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(toolbar != null){
            toolbar.setTitle("");
            toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addgroup_menu,menu);
        return true;
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.task_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //TODO 传递新清单数据回MainActivity
                //TODO 存储清单相关信息
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.id_add_group:
                        //TODO 传递新清单回
                        if(et_groupname.getText().toString().isEmpty()){
                           menuItem.setEnabled(false);
                        }else{
                            //有内容则传回MainActivity
                            String groupName = et_groupname.getText().toString();
                            DataUtil.dataUtilInstance.addGroupItem(groupName);
                            Intent intent = new Intent(AddGroupActivity.this, MainActivity.class);
//                            intent.putExtra("goupName",groupName);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
