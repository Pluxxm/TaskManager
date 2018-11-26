package com.example.taskmanager.network.apis;

import android.util.Log;


import com.example.taskmanager.network.model.LoginModel;
import com.example.taskmanager.network.services.HttpUtil;
import com.example.taskmanager.network.services.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 76952 on 2018/11/24.
 */

public class Login {
    public static LoginModel loginApi(String account, String password){
        JSONObject params = new JSONObject();
        try{
            params = new JSONObject()
                    .put("account", account)
                    .put("password", password);
        } catch (JSONException e){
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, params.toString());

        String json = HttpUtil.post(Url.login, body);
        return new Gson().fromJson(json, LoginModel.class);
    }
}
