package com.example.taskmanager.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager.AccountDetail;
import com.example.taskmanager.DataUtil;
import com.example.taskmanager.FirstPage;
import com.example.taskmanager.LoginActivity;
import com.example.taskmanager.R;
import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.widget.CommonItem;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 76952 on 2018/12/3.
 */

public class AccountFragment extends Fragment {
    private CommonItem accountItem;
    private CommonItem logoutItem;
    private View rootView = null;
    // 设置alertDialog所需
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.account_fragment, null);
        initView();
        return rootView;
    }
    private void initView(){
        accountItem = (CommonItem) rootView.findViewById(R.id.ci_account_detail);
        accountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountDetail.class);
                startActivity(intent);
            }
        });
        logoutItem = (CommonItem) rootView.findViewById(R.id.ci_log_out);
        logoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutModal();
            }
        });
    }
    /*
    * Step 1：创建AlertDialog.Builder对象；
    Step 2：调用setIcon()设置图标，setTitle()或setCustomTitle()设置标题；
    Step 3：设置对话框的内容：setMessage()还有其他方法来指定显示的内容；
    Step 4：调用setPositive/Negative/NeutralButton()设置：确定，取消，中立按钮；
    Step 5：调用create()方法创建这个对象，再调用show()方法将对话框显示出来；
    * */
    private void showLogoutModal() {
        alert = null;
        builder = new AlertDialog.Builder(getContext());
        alert = builder.setTitle("登出")
                .setMessage("你确定要登出？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("button", "取消");
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("button", "确定");
                        // 应该在此处添加退出登录的代码
                        // 清除存储本地的信息
                        removeInfo();
                        Intent intent = new Intent(getActivity(), FirstPage.class);
                        startActivity(intent);
                    }
                }).create();
        alert.show();
        // 必须show 之后才可以调用getButton
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    // 清除缓存在本地的密码
    private void removeInfo(){
        SharedPreferences.Editor user = getActivity().getSharedPreferences("user_data",MODE_PRIVATE).edit();
        user.remove("password");
        user.remove("name");
        user.apply();
    }
}
