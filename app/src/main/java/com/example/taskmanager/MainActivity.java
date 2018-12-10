package com.example.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.adapter.TaskRecyclerviewAdapter;
import com.example.taskmanager.bean.TaskGroupBean;
import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.fragment.AddTaskFragment;
import com.example.taskmanager.network.apis.AnswerRequest;
import com.example.taskmanager.network.apis.AskingInvitation;
import com.example.taskmanager.network.apis.DeleteMenu;
import com.example.taskmanager.network.apis.GetMenus;
import com.example.taskmanager.network.model.AskingModel;
import com.example.taskmanager.network.model.BaseHttpModel;
import com.example.taskmanager.network.model.MenuModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerview;
    private TaskRecyclerviewAdapter taskRecyclerviewAdapter;
    //recyclerview中的数据
    private List<TaskGroupBean> mList = new ArrayList<TaskGroupBean>();
    private Toolbar mToolbar;
    ArrayList<String> textList = new ArrayList<>();
    UserBean loginer;
    private FloatingActionButton faBtn;
    private TextView userName;
    private ImageView userAvatar;
    //团队协作
    String teamName;
    String creatorName;
    int teamId;

    Timer timer = new Timer();
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    timer.cancel();
                    showIfAcceptInvitationModel();
                    break;
                default:
                    break;
            }
        }
    };

    // { menuId, name}
    private int userId =0;
    private ArrayList<MenuModel.menuItem> menuItemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            textList = bundle.getStringArrayList("SelectList");
//        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                boolean flag = askingThread();
                Log.i("flag", flag+"");
                if(flag){
                    myHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task, 0, 3000);
        getAllMenus();
        getLoginer();
        initToolbar();
        // initViews();
    }
    //获取当前登录者的头像与名称
    //当前为fake数据
    private void getLoginer() {
        String name = "";
        String account = "";
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref != null) {
            name = pref.getString("name","");
            account = pref.getString("account", "");
        }
        loginer = new UserBean(DataUtil.dataUtilInstance.getAvatarRandom(), name, account);
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
        userAvatar = mToolbar.findViewById(R.id.id_userHeader);
        userName.setText(DataUtil.dataUtilInstance.getLoginer().getName());
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initList() {
        mList = new ArrayList<TaskGroupBean>();
        mList.add(new TaskGroupBean(R.drawable.icon_create_new_group,"创建新清单"));
//        String groupName = getIntent().getStringExtra("groupName");
//        if(!groupName.isEmpty() && groupName != null){
//            textList.add(groupName);
//        }
        textList = DataUtil.dataUtilInstance.getGroup();
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
                    intent.putExtra("menuId", menuItemArrayList.get(position-1).getMenu_id());
                    intent.putExtra("userId", userId);
                    Log.i("menuId", menuItemArrayList.get(position-1).getMenu_id()+"");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog alert = null;
                alert = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // taskRecyclerviewAdapter.removeData(position);
                                deleteMenu(menuItemArrayList.get(position-1).getMenu_id(), position);
                            }
                        }).create();
                alert.show();
                alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
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

    public void getAllMenus(){
        String account = "";
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref != null) {
            account = pref.getString("account","");
        }
        final String finalAccount = account;
        new Thread(new Runnable() {
            @Override
            public void run() {
                MenuModel menuModel = GetMenus.getMenusApi(finalAccount);
                if (menuModel.getCodeText().equals("000000")){
                    // 获取成功
                    textList = new ArrayList<String>();
                    MenuModel.Data data = menuModel.getData();
                    // 存储userId
                    userId = data.getUserId();
                    loginer.setUserId(userId);
                    DataUtil.dataUtilInstance.setLoginer(loginer);

                    ArrayList<com.example.taskmanager.network.model.MenuModel.menuItem> menus = data.getMenus();
                    menuItemArrayList = menus;
                    DataUtil.dataUtilInstance.setMenuList(menuItemArrayList);
                    for (int i=0;i<menus.size();i++){
                        Log.i("menu_id", menus.get(i).getMenu_id()+"");
                        Log.i("name", menus.get(i).getName()+"");
                        textList.add(menus.get(i).getName());
                    }
                    DataUtil.dataUtilInstance.setGroup(textList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initList();
                            initViews();
                        }
                    });
                }
            }
        }).start();
    }

    public void deleteMenu(int menuId, int position){
        final int finalMenuId = menuId;
        final int finalPosition = position;
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpModel deleteMenuModel = DeleteMenu.deleteMenu(finalMenuId);
                Log.i("errCode", deleteMenuModel.getCodeText());
                Log.i("errMsg", deleteMenuModel.getMessageText());
                if(deleteMenuModel.getCodeText().equals("000000")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            taskRecyclerviewAdapter.removeData(finalPosition);
                            Toast.makeText(MainActivity.this, "删除成功",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    // getAllMenus();
                }
            }
        }).start();
    }

    public boolean askingThread(){
        String account = "";
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref != null) {
            account = pref.getString("account","");
        }
        final String finalAccount = account;
        Log.i("account", account);
        AskingModel askingInvitation = AskingInvitation.askingInvitation(finalAccount);
        Log.i("ask", askingInvitation.getCodeText());
        Log.i("ask", askingInvitation.getMessageText());
        if(askingInvitation.getData().getStatus()){
            creatorName = askingInvitation.getData().getCreatorName();
            teamName = askingInvitation.getData().getTeamName();
            teamId = askingInvitation.getData().getTeamId();
        }
        return askingInvitation.getData().getStatus();
    }

    private void showIfAcceptInvitationModel() {
        android.app.AlertDialog alert = null;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        alert = builder.setTitle("邀请")
                .setMessage("你是否接受"+creatorName+"邀请,共同协作"+teamName+"?")
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("button", "拒绝");
                        answerInvitation(false);
                        timer = new Timer();
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                boolean flag = askingThread();
                                Log.i("flag", flag+"");
                                if(flag){
                                    myHandler.sendEmptyMessage(0);
                                }
                            }
                        };
                        timer.schedule(task, 0, 3000);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("button", "确定");
                        answerInvitation(true);
                        timer = new Timer();
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                boolean flag = askingThread();
                                Log.i("flag", flag+"");
                                if(flag){
                                    myHandler.sendEmptyMessage(0);
                                }
                            }
                        };
                        timer.schedule(task, 0, 3000);
                    }
                }).create();
        alert.show();
        // 必须show 之后才可以调用getButton
        alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    private void answerInvitation(final boolean answer){
        String account = "";
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref != null) {
            account = pref.getString("account","");
        }
        final String finalAccount = account;
        final int finalTeamId = teamId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpModel answerInvitation = AnswerRequest.answerRequest(finalAccount,finalTeamId,answer);
                Log.i("answerInvitation", answerInvitation.getMessageText());
                Log.i("answerInvitation", answerInvitation.getCodeText());
                if(answerInvitation.getCodeText().equals("000000")){
                    getAllMenus();
                }
            }
        }).start();
    }
}
