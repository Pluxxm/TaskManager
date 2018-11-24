package com.example.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.adapter.TaskRecyclerviewAdapter;
import com.example.taskmanager.bean.TaskGroupBean;
import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.fragment.AddTaskFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import circletextimage.viviant.com.circletextimagelib.view.CircleTextImage;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerview;
    private TaskRecyclerviewAdapter taskRecyclerviewAdapter;
    //recyclerview中的数据
    private List<TaskGroupBean> mList;
    private Toolbar mToolbar;
    ArrayList<String> textList;
    UserBean loginer;
    private FloatingActionButton faBtn;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            textList = bundle.getStringArrayList("SelectList");
//        }

        getLoginer();
        initList();
        initToolbar();
        initViews();
    }
    //获取当前登录者的头像与名称
    //当前为fake数据
    private void getLoginer() {
        loginer = new UserBean(DataUtil.dataUtilInstance.getAvatarRandom(), "啦啦啦啦");
        //存到DataUtil方便存取
        DataUtil.dataUtilInstance.setLoginer(loginer);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(mToolbar != null){
            mToolbar.setTitle("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initToolbar() {
        mToolbar = (Toolbar)this.findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.id_message:
                        Toast.makeText(MainActivity.this, "action_message",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.id_setting:
                        Toast.makeText(MainActivity.this, "action_settings",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        userName = mToolbar.findViewById(R.id.id_userName);
        userName.setText(DataUtil.dataUtilInstance.getLoginer().getName());

    }

    private void initList() {
        mList = new ArrayList<TaskGroupBean>();

        mList.add(new TaskGroupBean(R.drawable.icon_create_new_group,"创建新清单"));
//        String groupName = getIntent().getStringExtra("groupName");
//        if(!groupName.isEmpty() && groupName != null){
//            textList.add(groupName);
//        }
        textList = new ArrayList<>();
        textList.addAll(DataUtil.dataUtilInstance.getGroup());
        Log.d("Check",textList.size()+"");
        if (textList.size() != 0){
            for(int i = 0; i < textList.size(); ++i){
                mList.add(new TaskGroupBean(R.drawable.icon_task_group2,textList.get(i)));
            }
        }else{
            Toast.makeText(MainActivity.this,"没有默认任务组",Toast.LENGTH_SHORT).show();
        }


    }

    private void initViews() {
        taskRecyclerview = (RecyclerView)findViewById(R.id.id_task_recyclerview);
        //设置布局管理器
        taskRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //设置item增加和删除的动画
        taskRecyclerview.setItemAnimator(new DefaultItemAnimator());
        taskRecyclerviewAdapter = new TaskRecyclerviewAdapter(mList, this);
        //设置分割线
        taskRecyclerview.addItemDecoration(new DividerItemDecoration
                (MainActivity.this, DividerItemDecoration.VERTICAL_LIST,100));
        //设置适配器
        taskRecyclerview.setAdapter(taskRecyclerviewAdapter);
        //监听点击事件
        taskRecyclerviewAdapter.setOnItemClickListener(new TaskRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "点击第"+(position+1)+"条"
//                        , Toast.LENGTH_SHORT).show();
                if(position != 0) {
                    Intent intent = new Intent(MainActivity.this, ItemClickActivity.class);
                    intent.putExtra("groupName", mList.get(position).getName());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskRecyclerviewAdapter.removeData(position);
                            }
                        })
                        .show();
            }
        });

        faBtn = findViewById(R.id.id_fabtn);
        faBtn.show();
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faBtn.hide();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AddTaskFragment addTaskFragment = new AddTaskFragment();
                ft.replace(R.id.content, addTaskFragment,"add");
                ft.commit();
            }
        });
    }

    public FloatingActionButton getFaBtn(){
        return faBtn;
    }
}
