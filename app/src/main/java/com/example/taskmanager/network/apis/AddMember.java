package com.example.taskmanager.network.apis;

import android.util.Log;

import com.example.taskmanager.network.model.BaseHttpModel;
import com.example.taskmanager.network.services.HttpUtil;
import com.example.taskmanager.network.services.Url;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 76952 on 2018/12/6.
 */

public class AddMember {
    public static BaseHttpModel addMember(int menuId, String inviteMember){
        JSONObject params = new JSONObject();
        try{
            params = new JSONObject()
                    .put("menuId", menuId)
                    .put("inviteMember", inviteMember);
        } catch (JSONException e){
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, params.toString());

        String json = HttpUtil.post(Url.addTeamMember, body);
        Log.i("json", json);
        return new Gson().fromJson(json, BaseHttpModel.class);
    }
}
