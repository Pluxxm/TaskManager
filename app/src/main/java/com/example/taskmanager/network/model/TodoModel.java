package com.example.taskmanager.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 76952 on 2018/12/3.
 */

public class TodoModel extends BaseHttpModel {
    public class todoItem {
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
    @SerializedName("data")
    private ArrayList<todoItem> todos;
    public ArrayList<todoItem> getTodos(){return this.todos;}
    public void setTodos(ArrayList<todoItem> todos) {
        this.todos = todos;
    }

}
