package com.example.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.taskmanager.adapter.ChangeBackgroundAdapter;

import java.util.ArrayList;
import java.util.List;

import skin.support.content.res.SkinCompatUserThemeManager;

public class ChangeBackgroundActivity extends AppCompatActivity {

    private RelativeLayout rl_change; //展示切换的背景图
    private RecyclerView rv_change; //切换背景的recyclerview
    private ChangeBackgroundAdapter backgroundAdapter;
    private RelativeLayout itemclick_rl; //ItemClickActivity中的背景
    private List<Integer> backgroundIdList;
    private List<Boolean> isSelectedList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);

        initList();
        initView();
    }

    private void initList() {
        backgroundIdList = new ArrayList<>();
        backgroundIdList.add(R.drawable.background);
        backgroundIdList.add(R.drawable.background_1);
        backgroundIdList.add(R.drawable.background_2);
        backgroundIdList.add(R.drawable.background_3);
        backgroundIdList.add(R.drawable.background_4);
        backgroundIdList.add(R.drawable.background_5);
        backgroundIdList.add(R.drawable.background_6);

        isSelectedList = new ArrayList<>();
        for(int i = 0; i < backgroundIdList.size(); i++){
            if(i == 0){
                isSelectedList.add(true);
            }else{
                isSelectedList.add(false);
            }
        }
    }

    private void initView() {
        itemclick_rl = findViewById(R.id.itemclick_rl);
        rl_change = findViewById(R.id.change_background_rl);
        rv_change = findViewById(R.id.change_background_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_change.setLayoutManager(layoutManager);

        backgroundAdapter = new ChangeBackgroundAdapter(ChangeBackgroundActivity.this
                , backgroundIdList, isSelectedList);
        rv_change.setAdapter(backgroundAdapter);

        backgroundAdapter.setOnItemClickListener(new ChangeBackgroundAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                rl_change.setBackgroundResource(backgroundIdList.get(position));
                //itemclick_rl.setBackgroundResource(backgroundIdList.get(position));
                DataUtil.dataUtilInstance.setBackgroundID(backgroundIdList.get(position));
                for (int i = 0; i < isSelectedList.size(); ++i){
                    if (i == position){
                        isSelectedList.set(i, true);
                    }else{
                        isSelectedList.set(i, false);
                    }
                }
                backgroundAdapter.notifyDataSetChanged();
            }
        });
    }
}
