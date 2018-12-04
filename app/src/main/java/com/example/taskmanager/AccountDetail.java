package com.example.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.network.apis.ChangeUserName;
import com.example.taskmanager.network.model.BaseHttpModel;
import com.example.taskmanager.widget.CommonItem;
import com.example.taskmanager.widget.MyAlertDialog;

public class AccountDetail extends AppCompatActivity implements View.OnClickListener{

    private CommonItem ci_changeName;
    private CommonItem ci_changePwd;
    private MyAlertDialog myAlertDialog;
    private ImageView iv_return;
    private UserBean user = DataUtil.dataUtilInstance.getLoginer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        initView();
    }

    private void initView() {
        ci_changeName = (CommonItem) findViewById(R.id.change_name);
        ci_changePwd = (CommonItem) findViewById(R.id.change_pwd);
        ci_changeName.setOnClickListener(this);
        ci_changePwd.setOnClickListener(this);
        iv_return = (ImageView) findViewById(R.id.bar_return);
        iv_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_name:
                myAlertDialog = new MyAlertDialog(AccountDetail.this);
                myAlertDialog.show();
                myAlertDialog.setTitle("更改名字");
                myAlertDialog.setContent(user.getName());
                //myAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", DialogInterface.OnClickListener());
                break;
            case R.id.change_pwd:
                Intent intent1 = new Intent(AccountDetail.this, ChangePwd.class);
                startActivity(intent1);
                break;
            case R.id.bar_return:
                Intent intent = new Intent(AccountDetail.this, UserInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }


}
