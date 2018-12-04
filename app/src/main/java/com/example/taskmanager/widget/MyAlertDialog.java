package com.example.taskmanager.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.DataUtil;
import com.example.taskmanager.R;
import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.network.apis.ChangeUserName;
import com.example.taskmanager.network.model.BaseHttpModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 76952 on 2018/12/3.
 */

public class MyAlertDialog extends AlertDialog implements View.OnClickListener{
    private Context mContext;
    private TextView tv_cancel;
    private TextView tv_confirm;
    private TextView tv_title;
    private EditText et_content;
    private UserBean user =  DataUtil.dataUtilInstance.getLoginer();

    public MyAlertDialog(Context context){
        super(context);
        mContext = context;
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_alert_dialog);
        initView();
    }
    private void initView(){
        tv_cancel = (TextView) findViewById(R.id.my_dialog_cancel);
        tv_confirm = (TextView) findViewById(R.id.my_dialog_confirm);
        tv_title = (TextView) findViewById(R.id.my_dialog_title);
        et_content = (EditText) findViewById(R.id.my_dialog_content);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }
    private void doOk() {
        changeName(et_content.getText().toString());
        dismiss();
    }

    private void doCancel() {
        dismiss();
        Toast.makeText(mContext, "Cancel", Toast.LENGTH_LONG).show();
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }
    public void setContent(String content){
        et_content.setText(content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_dialog_cancel:
                doCancel();
                break;
            case R.id.my_dialog_confirm:
                // 在此处添加修改名字的代码
                doOk();
                break;
        }
    }

    public void changeName(String newName){
        final String finalName = newName;
        final String finalAccount = user.getMail();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpModel changeNameModel = ChangeUserName.changeUserName(finalAccount, finalName);
                Log.i("changeName", changeNameModel.getCodeText());
                Log.i("changeName", changeNameModel.getMessageText());
                if(changeNameModel.getCodeText().equals("000000")){
                    // 更改本地缓存和userBean
                    changeUserInfo(finalName);
                }
            }
        }).start();
    }

    public void changeUserInfo(String newName){
        SharedPreferences.Editor editor = getContext().getSharedPreferences("user_data",MODE_PRIVATE).edit();
        editor.remove("name");
        editor.putString("name", newName);
        editor.apply();
        user.setName(newName);
    }
}
