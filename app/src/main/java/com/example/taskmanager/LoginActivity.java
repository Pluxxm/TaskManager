package com.example.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskmanager.network.apis.GetMenus;
import com.example.taskmanager.network.apis.Login;
import com.example.taskmanager.network.apis.Test;
import com.example.taskmanager.network.model.LoginModel;
import com.example.taskmanager.network.model.MenuModel;
import com.example.taskmanager.util.Base64;
import com.example.taskmanager.util.Token;
import com.example.taskmanager.util.UserId;

public class LoginActivity extends AppCompatActivity {

    private EditText login_account;
    private EditText login_pwd;
    private Button btn_login;
    private Base64 base64;
    Token token = Token.getInstance();
    UserId userId = UserId.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
    }
    private void initData(){
        login_account = findViewById(R.id.login_account);
        login_pwd = findViewById(R.id.login_pwd);
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref != null) {
            String account = pref.getString("account","");
            login_account.setText(account);
        }
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 此处添加发送登录的请求，成功的话执行下边的代码，并且把token加入到请求的头部
                String account = login_account.getText().toString();
                String password = login_pwd.getText().toString();
                login(account, password);
            }
        });
    }

    public void login(final String account, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // test network
                String ip = Test.getIp();
                Log.i("demo", String.format("getIp: %s", ip));
                // send login request
                LoginModel loginModel = Login.loginApi(account, password);
                if (loginModel.getCodeText().equals("000000")) {
                    // 登录成功，把account,name,pwd都保存下，token,userId,
                    LoginModel.Data loginData = loginModel.getDataText();
                    saveUserInfo(account,password);
                    token.setToken(loginData.getTokenText());
                    userId.setUserId(Integer.parseInt(loginData.getUserIdText()));
                    //  isFirstTime为true 跳转到select
                    if (loginData.getIsFirstTime()){
                        Intent intent = new Intent(LoginActivity.this, SelectGroupActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }).start();
    }

    private void saveUserInfo(String account, String password ){
        SharedPreferences.Editor user = getSharedPreferences("user_data",MODE_PRIVATE).edit();
        user.putString("account", account);
        user.putString("password", base64.encode(password));
        user.apply();
    }
}
