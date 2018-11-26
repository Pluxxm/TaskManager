package com.example.taskmanager.util;

/**
 * Created by 76952 on 2018/11/26.
 */

// version 1.5
public class Token {
    private volatile static Token singleton = new Token();
    private Token()  {    }
    public static Token getInstance()   {
        return singleton;
    }
    public static String token = "";
    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }
}
