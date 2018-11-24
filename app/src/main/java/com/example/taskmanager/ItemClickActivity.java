package com.example.taskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.adapter.TaskDetailRecyclerviewAdapter;
import com.example.taskmanager.bean.SortBean;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemClickActivity extends AppCompatActivity {
    private Toolbar taskToolbar;
    private EditText addTask_et;
    private Button btn_forsure;
    private RecyclerView rv_tasks;
    private RecyclerView rv_tasks_done;
    private Button show_finishedBtn;
    //TODO 自定义view 选择框+文本+星标
    private List<String> taskList;
    private List<String> taskDone;
    public TaskDetailRecyclerviewAdapter taskDetailRecyclerviewAdapter;
    private TaskDetailRecyclerviewAdapter adapterDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemclick);

        initList();
        initToolbar();
        initView();
        initButton();
    }

    private void initButton() {
        show_finishedBtn = (Button)findViewById(R.id.showFinishedBtn);
        show_finishedBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(show_finishedBtn.getText().equals("显示已完成任务")) {
                    rv_tasks_done.setVisibility(View.VISIBLE);
                    show_finishedBtn.setText("隐藏已完成任务");
                }else if(show_finishedBtn.getText().equals("隐藏已完成任务")){
                    rv_tasks_done.setVisibility(View.GONE);
                    show_finishedBtn.setText("显示已完成任务");
                }
            }
        });
    }

    //fake数据
    private void initList() {
        //TODO 请求网络拿到list 赋值给DataUtil
        taskList = new ArrayList<>();
        taskList.add("今天我要睡觉");
        taskList.add("今天我要学习");
        taskList.add("看书");
        taskList.add("反反复复付付");
        taskList.add("少时诵诗书");
        DataUtil.dataUtilInstance.setTaskTodo(taskList);

        taskDone = new ArrayList<>();
        taskDone.add("啦啦啦啦啦");
        taskDone.add("嘎嘎嘎嘎嘎过");
        taskDone.add("生死时速是");
        taskDone.add("柔柔弱弱若若");
        DataUtil.dataUtilInstance.setTaskDone(taskDone);
    }

    private void initView() {
        Log.d("Add",DataUtil.dataUtilInstance.getTaskTodo().toString());
        addTask_et = (EditText)findViewById(R.id.id_et_addTask);
        //Recyclerview的设置
        rv_tasks = (RecyclerView)findViewById(R.id.id_rv_tasks);
        rv_tasks_done = (RecyclerView)findViewById(R.id.id_rv_tasks_done);

        rv_tasks.setLayoutManager(new LinearLayoutManager(this));
        //设置item增加和删除的动画
        rv_tasks.setItemAnimator(new DefaultItemAnimator());
        taskDetailRecyclerviewAdapter = new TaskDetailRecyclerviewAdapter(this, DataUtil.dataUtilInstance.getTaskTodo()
                , DataUtil.dataUtilInstance.getTaskDone(), 0, adapterDone);
        //设置分割线
        rv_tasks.addItemDecoration(new DividerItemDecoration
                (ItemClickActivity.this, DividerItemDecoration.VERTICAL_LIST,0));
        //设置适配器
        rv_tasks.setAdapter(taskDetailRecyclerviewAdapter);
        //设置禁止滑动
        rv_tasks.setNestedScrollingEnabled(false);
        taskDetailRecyclerviewAdapter.setOnItemClickListener(new TaskDetailRecyclerviewAdapter
                .OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ItemClickActivity.this, "点击第"+(position+1)+"条"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(ItemClickActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskDetailRecyclerviewAdapter.removeData(i);
                            }
                        })
                        .show();
            }
        });

        rv_tasks_done.setLayoutManager(new LinearLayoutManager(this));
        rv_tasks_done.setItemAnimator(new DefaultItemAnimator());
        adapterDone = new TaskDetailRecyclerviewAdapter(this, DataUtil.dataUtilInstance.getTaskTodo()
                ,DataUtil.dataUtilInstance.getTaskDone(), 1, taskDetailRecyclerviewAdapter);
        rv_tasks_done.addItemDecoration(new DividerItemDecoration(ItemClickActivity.this,
                DividerItemDecoration.VERTICAL_LIST, 0));
        rv_tasks_done.setAdapter(adapterDone);
        rv_tasks_done.setNestedScrollingEnabled(false);
        adapterDone.setOnItemClickListener(new TaskDetailRecyclerviewAdapter
                .OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ItemClickActivity.this, "点击第"+(position+1)+"条"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(ItemClickActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskDetailRecyclerviewAdapter.removeData(i);
                            }
                        })
                        .show();
            }
        });

        btn_forsure = (Button)findViewById(R.id.btn_forsure);
        btn_forsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!addTask_et.getText().toString().isEmpty()){
                    taskList.add(addTask_et.getText().toString());
                    Log.d("new",addTask_et.getText().toString());
                    taskDetailRecyclerviewAdapter.notifyDataSetChanged();
                    addTask_et.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

    }

    //接受清单名称 设置toolbar标题
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(taskToolbar != null){
            String title = getIntent().getStringExtra("groupName");
            taskToolbar.setTitle(title);
            taskToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    //设置toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }

    //设置toolbar并设置toolbar上点击事件
    private void initToolbar() {
        taskToolbar = (Toolbar)findViewById(R.id.task_toolbar);
        setSupportActionBar(taskToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        taskToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //TODO 传递数据回MainActivity
                //TODO 存储清单相关信息
            }
        });
        taskToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.id_add_collegue:
                        Intent intent = new Intent(ItemClickActivity.this,AddFriendsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.id_order:
                        //对任务进行首字母排序
                        List<SortBean> taskListPinyin = new ArrayList<>();
                        for (int i = 0; i < taskList.size(); i++){
                            //获取任务首字母
                            String taskF = PinyinUtil.getPingYin(taskList.get(i))
                                    .substring(0,1).toUpperCase();
                            if (!taskF.matches("[A-Z]")) {
                                taskF = "#";
                            }
                            taskListPinyin.add(new SortBean(taskList.get(i), taskF));
                        }
                        Collections.sort(taskListPinyin,new PinyinComparator());
                        taskList.clear();
                        for (int j = 0; j < taskListPinyin.size(); ++j){
                            taskList.add(taskListPinyin.get(j).getTask());
                        }
                        taskDetailRecyclerviewAdapter.notifyDataSetChanged();
                        break;
                    case R.id.id_tasktoolbar_setting:
                        Toast.makeText(ItemClickActivity.this, "action_toolbar_setting",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.id_send:
                        Toast.makeText(ItemClickActivity.this, "action_send",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskDetailRecyclerviewAdapter.notifyDataSetChanged();
        adapterDone.notifyDataSetChanged();
    }
}
