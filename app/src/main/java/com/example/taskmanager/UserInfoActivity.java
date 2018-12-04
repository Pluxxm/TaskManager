package com.example.taskmanager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanager.adapter.userInfoFragmentAdapter;
import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.fragment.AccountFragment;
import com.example.taskmanager.fragment.GenerateFragment;
import com.example.taskmanager.fragment.OthersFragment;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private userInfoFragmentAdapter userInfoFragmentAdapter;
    // tabLayout标签
    private String[] titles = new String[]{"账户","通用","其他"};
    // fragments
    private List<Fragment> fragments = new ArrayList<>();

    private ImageView iv_return;
    private ImageView iv_avatar;
    private TextView tv_name;

    // 个人信息
    private UserBean user = DataUtil.dataUtilInstance.getLoginer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initTab();
    }

    private void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        iv_return = (ImageView) findViewById(R.id.return_arrow);
        iv_avatar = (ImageView) findViewById(R.id.avatar);
        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, AccountDetail.class);
                startActivity(intent);
            }
        });
        tv_name = (TextView) findViewById(R.id.name);
        tv_name.setText(user.getName());
    }

    private void initTab() {
        //设置TabLayout标签的显示方式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String tab:titles){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        //设置TabLayout点击事件
        tabLayout.addOnTabSelectedListener(this);

        fragments.add(new AccountFragment());
        fragments.add(new GenerateFragment());
        fragments.add(new OthersFragment());

        userInfoFragmentAdapter = new userInfoFragmentAdapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(userInfoFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
