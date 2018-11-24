package com.example.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.taskmanager.adapter.AddFriendsRecyclerviewAdapter;
import com.example.taskmanager.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class AddFriendsActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView rv_addfriends;
    private AddFriendsRecyclerviewAdapter friendsRecyclerviewAdapter;
    private List<UserBean> userBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);

        initList();
        initView();
    }

    private void initList() {
        //TODO 网络请求任务组中已有成员列表
        //fake数据
        userBeanList = new ArrayList<>();
        UserBean userBean = DataUtil.dataUtilInstance.getLoginer();
        userBean.setMail("123456789@scut.com");
        userBeanList.add(userBean);
        userBeanList.add(new UserBean(DataUtil.dataUtilInstance.getAvatarRandom(),"哈哈哈","123546879@scut.com"));
    }

    private void initView() {
        searchView = findViewById(R.id.searchview);
        // 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
                //TODO 搜索输入的string 向所指用户发送邀请
            }
        });
        // 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

        //TODO 获得已有用户列表 显示在recyclerview中
        rv_addfriends = findViewById(R.id.rv_addfriends);
        rv_addfriends.setLayoutManager(new LinearLayoutManager(this));
        rv_addfriends.setItemAnimator(new DefaultItemAnimator());
        friendsRecyclerviewAdapter = new AddFriendsRecyclerviewAdapter(this, userBeanList);
        rv_addfriends.addItemDecoration(new DividerItemDecoration
                (AddFriendsActivity.this, DividerItemDecoration.VERTICAL_LIST,100));
        //设置适配器
        rv_addfriends.setAdapter(friendsRecyclerviewAdapter);
    }
}
