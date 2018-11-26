package com.example.taskmanager.network.apis;

import com.example.taskmanager.network.model.MenuModel;
import com.example.taskmanager.network.services.HttpUtil;
import com.example.taskmanager.network.services.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 76952 on 2018/11/26.
 */

public class GetMenus {
    public static MenuModel getMenusApi() {
        String json = HttpUtil.get(Url.getMenus);
        try {
            JSONObject obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json, MenuModel.class);
    }
}
