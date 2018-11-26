package com.example.taskmanager.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 76952 on 2018/11/26.
 */

public class MenuModel extends BaseHttpModel {
    private class todoItem {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("isFinished")
        private Boolean isFinished;
        public int getId(){return id;}
        public void setId(int id){this.id = id;}
        public String getTitle(){return this.title;}
        public void setTitle(String title){this.title = title; }
        public Boolean getIsFinished(){return this.isFinished;}
        public void setIsFinished(Boolean isFinished){this.isFinished = isFinished;}
    }
    public class menuItem {
        @SerializedName("menu_id")
        private int menu_id;
        @SerializedName("name")
        private String name;
        @SerializedName("todos")
        private ArrayList<todoItem> todos;
        public int getMenu_id(){return this.menu_id;}
        public void setMenu_id(int menu_id){this.menu_id = menu_id;}
        public String getName(){return this.name;}
        public void setName(String name){this.name = name;}
        public ArrayList<todoItem> getTodos(){return this.todos;}
        public void setTodos(ArrayList<todoItem> todos){this.todos = todos;}
    }
    public class Data {
        @SerializedName("user_id")
        private int userId;
        @SerializedName("account")
        private String account;
        @SerializedName("menus")
        private ArrayList<menuItem> menus;
        public int getUserId(){return userId;}
        public void setUserId(int id){this.userId = id;}
        public String getAccount(){return this.account;}
        public void setAccount(String account){this.account = account;}
        public ArrayList<menuItem> getMenus(){return this.menus;}
        public void setMenus(ArrayList<menuItem> menus){this.menus = menus;}
    }
    @SerializedName("data")
    private Data data;
    public Data getData(){return this.data;}
    public void setData(Data data){this.data = data;}
}
