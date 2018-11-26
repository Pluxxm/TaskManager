package com.example.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskmanager.network.apis.Register;
import com.example.taskmanager.network.model.BaseHttpModel;
import com.example.taskmanager.util.Base64;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_account;
    private EditText et_password;
    private Button btn_register;
    private Base64 base64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    private void init() {
        et_name = (EditText) findViewById(R.id.name);
        et_account = (EditText) findViewById(R.id.account);
        et_password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.confirm);

        // 点击注册，发送网络请求，注册成功后返回到login界面
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                register(name, account, password);
            }
        });
    }

    private void saveUserInfo(String name, String account ){
        SharedPreferences.Editor user = getSharedPreferences("user_data",MODE_PRIVATE).edit();
        user.putString("name", name);
        user.putString("account", account);
        user.apply();
    }

    private void register(final String name, final String account, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpModel registerModel = Register.registerApi(name, account, password);
                if (registerModel.getCodeText().equals("000000")){
                    // 注册成功，保存name和account
                    saveUserInfo(name, account);
                    // 跳转到登录页面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }).start();
    }
}
