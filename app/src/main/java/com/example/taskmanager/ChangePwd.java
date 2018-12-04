package com.example.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.network.apis.ChangeUserPwd;
import com.example.taskmanager.network.model.BaseHttpModel;
import com.example.taskmanager.util.Base64;

public class ChangePwd extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_return;
    private ImageView iv_confirm;
    private EditText et_currentPwd;
    private EditText et_newPwd;
    private EditText et_againPwd;
    private EditText[] etArray;
    private String[] hintStr = new String[]{"当前密码","新密码","确认密码"};
    private TextView tv_currentPwd;
    private TextView tv_newPwd;
    private TextView tv_againPwd;
    private TextView[] hintTextView;
    private UserBean user = DataUtil.dataUtilInstance.getLoginer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initView();
    }
    private void initView(){
        iv_return = (ImageView) findViewById(R.id.pwd_return);
        iv_return.setOnClickListener(this);

        iv_confirm = (ImageView) findViewById(R.id.confirm_change);
        iv_confirm.setOnClickListener(this);

        tv_currentPwd = (TextView) findViewById(R.id.tv_current_pwd);
        tv_newPwd = (TextView) findViewById(R.id.tv_new_pwd);
        tv_againPwd = (TextView) findViewById(R.id.tv_again_pwd);
        hintTextView = new TextView[]{tv_currentPwd, tv_newPwd, tv_againPwd};

        et_currentPwd = (EditText) findViewById(R.id.current_pwd);
        et_newPwd = (EditText) findViewById(R.id.new_pwd);
        et_againPwd = (EditText) findViewById(R.id.again_pwd);
        etArray = new EditText[]{et_currentPwd,et_newPwd,et_againPwd};
        // editText 获得焦点时hint消失，失去焦点时hint显示
        for (int i=0; i<etArray.length; i++){
            final int j = i;
            etArray[j].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        ((TextView)view).setHint("");
                        hintTextView[j].setVisibility(View.VISIBLE);
                    } else {
                        ((TextView)view).setHint(hintStr[j]);
                        hintTextView[j].setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pwd_return:
                Intent intent = new Intent(ChangePwd.this, AccountDetail.class);
                startActivity(intent);
                break;
            case R.id.confirm_change:
                //应该在此处添加修改密码的代码
                if (et_newPwd.getText().toString().equals(et_againPwd.getText().toString())){
                    changePwd();
                } else {
                    Toast.makeText(ChangePwd.this,"请两次输入的密码保持一致",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void changePwd(){
        final String account = user.getMail();
        final String pwd = et_currentPwd.getText().toString();
        final String newPwd = et_newPwd.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpModel changePwdModel = ChangeUserPwd.changeUserPwd(account,pwd,newPwd);
                Log.i("changepwd", changePwdModel.getCodeText());
                Log.i("changePwd", changePwdModel.getMessageText());
                if(changePwdModel.getCodeText().equals("000000")){
                    // 在此处更新用户信息
                    changeUserInfo(newPwd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePwd.this,"修改成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void changeUserInfo(String newPwd){
        SharedPreferences.Editor editor = getSharedPreferences("user_data",MODE_PRIVATE).edit();
        editor.remove("password");
        editor.putString("password", Base64.encode(newPwd));
        editor.apply();
    }

}
