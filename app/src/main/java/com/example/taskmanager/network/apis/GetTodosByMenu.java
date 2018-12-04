package com.example.taskmanager.network.apis;

import android.util.Log;

import com.example.taskmanager.network.model.TodoModel;
import com.example.taskmanager.network.services.HttpUtil;
import com.example.taskmanager.network.services.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 76952 on 2018/12/3.
 */

public class GetTodosByMenu {
    public static TodoModel getTodosByMenu(int menuId, int userId, int selection){
        JSONObject params = new JSONObject();
        try{
            params = new JSONObject()
                    .put("menuId", menuId)
                    .put("userId", userId)
                    .put("isFinished", selection);
        } catch (JSONException e){
            e.printStackTrace();
        }
        Log.i("params", params.toString());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, params.toString());
        Log.i("body", body.toString());

        String json = HttpUtil.post(Url.getTodosByMenu, body);
        Log.i("json",json);
        return new Gson().fromJson(json, TodoModel.class);
    }
}
