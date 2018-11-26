package com.example.taskmanager.network.apis;

import com.example.taskmanager.network.services.HttpUtil;
import com.example.taskmanager.network.services.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 76952 on 2018/11/24.
 */

public class Test {
    public static String getIp() {
        String json = HttpUtil.get(Url.test);
        String ip = null;
        try {
            JSONObject obj = new JSONObject(json);
            ip = (String) obj.get("origin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
