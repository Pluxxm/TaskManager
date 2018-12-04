package com.example.taskmanager;

import com.example.taskmanager.bean.UserBean;
import com.example.taskmanager.network.model.MenuModel;
import com.example.taskmanager.network.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    //一个清单组里完成与未完成的任务
//    private List<String> taskTodo;
//    private List<String> taskDone;
    private ArrayList<TodoModel.todoItem> taskTodo = new ArrayList<>();
    private ArrayList<TodoModel.todoItem> taskDone = new ArrayList<>();
    //所有的任务组
    private ArrayList<String> group;
    public static DataUtil dataUtilInstance = new DataUtil();
    //登录者的信息
    private UserBean loginer;
    //头像随机选取
    //TODO 发网络请求消息获取todo和done

    private ArrayList<Integer> avatar;
    private ArrayList<MenuModel.menuItem> menuList;

    private void initAvatarList(){
        avatar = new ArrayList<>();
        avatar.add(R.drawable.icon_personal);
        avatar.add(R.drawable.icon_personal_1);
        avatar.add(R.drawable.icon_personal_2);
        avatar.add(R.drawable.icon_personal_3);
        avatar.add(R.drawable.icon_personal_4);
    }

    public int getAvatarRandom(){
        initAvatarList();
        double b = Math.random();
        int i = (int)b*100;
        return avatar.get(i);
    }


    public ArrayList<String> getGroup() {
        return group;
    }

    public void addGroupItem(String groupName){
        group.add(groupName);
    }

    void setGroup(ArrayList<String> group) {
        this.group = group;
    }

    public UserBean getLoginer() {
        return loginer;
    }

    void setLoginer(UserBean loginer) {
        this.loginer = loginer;
    }

    private DataUtil() {
        taskDone = new ArrayList<>();
        taskTodo = new ArrayList<>();
        initAvatarList();
        group = new ArrayList<>();
    }

    public DataUtil(ArrayList<TodoModel.todoItem> taskTodo, ArrayList<TodoModel.todoItem> taskDone) {
        this.taskTodo = taskTodo;
        this.taskDone = taskDone;
    }

    public ArrayList<TodoModel.todoItem> getTaskTodo() {
        return taskTodo;
    }

    public void setTaskTodo(ArrayList<TodoModel.todoItem> taskTodo) {
        this.taskTodo = taskTodo;
    }

    public ArrayList<TodoModel.todoItem> getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(ArrayList<TodoModel.todoItem> taskDone) {
        this.taskDone = taskDone;
    }

    public ArrayList<MenuModel.menuItem> getMenuList() {return menuList;}

    public void setMenuList(ArrayList<MenuModel.menuItem> menuList) {this.menuList = menuList;}
}
