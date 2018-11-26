package com.example.taskmanager.network.services;

import com.example.taskmanager.util.UserId;

/**
 * Created by 76952 on 2018/11/24.
 */

public class Url {
    private static final String domain = "junescut.cn";
    private static final String protocol = "http://";
    private static final String port = ":13000";
    static UserId userId = UserId.getInstance();

    private static String catUrl(String url) {
        // http://junescut.cn:13000/user/creatUser
        return String.format("%s%s%s%s", protocol,domain,port,url);
    }

    public static final String test = "http://httpbin.org/get";
    public static final String login = catUrl("/user/login");
    public static final String register = catUrl("/user/createUser");
    public static final String getMenus = catUrl("/getAllTodos?userId=") + userId.getUserId();
}
