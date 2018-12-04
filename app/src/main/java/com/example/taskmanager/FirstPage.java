package com.example.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.taskmanager.network.apis.Login;
import com.example.taskmanager.network.model.LoginModel;
import com.example.taskmanager.util.Base64;
import com.example.taskmanager.util.Token;
import com.example.taskmanager.util.UserId;

public class FirstPage extends AppCompatActivity {

    private Button btn_register;
    private Button btn_login;
    private Base64 base64;
    Token token = Token.getInstance();
    UserId userId = UserId.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        init();
        //判断sharePerence是否存在账号密码,是的话发送登录请求，
        SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (pref != null) {
            String account = pref.getString("account","");
            String password = base64.decode(pref.getString("password", ""));
            // 发送登录请求
            if (!account.equals("") && !password.equals(""))
                // login(account, password);
            {
                Intent intent2 = new Intent(FirstPage.this, MainActivity.class);
                startActivity(intent2);
            }

        }
    }
    private void init() {
        btn_register = (Button) findViewById(R.id.first_register);
        btn_login = (Button) findViewById(R.id.first_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到register
                Intent intent = new Intent(FirstPage.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到login
                Intent intent = new Intent(FirstPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(final String account, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // send login request
                LoginModel loginModel = Login.loginApi(account, password);
                if (loginModel.getCodeText().equals("000000")) {
                    // 登录成功，把account,name,pwd都保存下，token,userId,
                    LoginModel.Data loginData = loginModel.getDataText();
                    token.setToken(loginData.getTokenText());
                    userId.setUserId(Integer.parseInt(loginData.getUserIdText()));
                    //  跳转到Home
                    Intent intent = new Intent(FirstPage.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }).start();
    }
}
