package com.example.taskmanager.util;

/**
 * Created by 76952 on 2018/11/26.
 */

public class UserId {
    private volatile static UserId singleton = new UserId();
    private UserId()  {    }
    public static UserId getInstance()   {
        return singleton;
    }
    public static int userId = 0;
    public int getUserId(){
        return userId;
    }
    public void setUserId(int id){
        this.userId = id;
    }
}
