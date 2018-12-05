package com.example.taskmanager.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 76952 on 2018/11/26.
 */

public class MenuModel extends BaseHttpModel {
    public class menuItem {
        @SerializedName("menu_id")
        private int menu_id;
        @SerializedName("name")
        private String name;

        public int getMenu_id(){return this.menu_id;}
        public void setMenu_id(int menu_id){this.menu_id = menu_id;}
        public String getName(){return this.name;}
        public void setName(String name){this.name = name;}
    }
    public class Data {
        @SerializedName("user_id")
        private int userId;
        @SerializedName("account")
        private String account;
        @SerializedName("menus")
        private ArrayList<MenuModel.menuItem> menus;
        public int getUserId(){return userId;}
        public void setUserId(int id){this.userId = id;}
        public String getAccount(){return this.account;}
        public void setAccount(String account){this.account = account;}
        public ArrayList<MenuModel.menuItem> getMenus(){return this.menus;}
        public void setMenus(ArrayList<MenuModel.menuItem> menus){this.menus = menus;}
    }
    @SerializedName("data")
    private Data data;
    public Data getData(){return this.data;}
    public void setData(Data data){this.data = data;}
}
