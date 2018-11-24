package com.example.taskmanager;

import com.example.taskmanager.bean.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataUtil {
    //一个清单组里完成与未完成的任务
    private List<String> taskTodo;
    private List<String> taskDone;
    //所有的任务组
    private List<String> group;
    public static DataUtil dataUtilInstance = new DataUtil();
    //登录者的信息
    private UserBean loginer;
    //头像随机选取
    //TODO 发网络请求消息获取todo和done

    private ArrayList<Integer> avatar;

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


    public List<String> getGroup() {
        return group;
    }

    public void addGroupItem(String groupName){
        group.add(groupName);
    }

    void setGroup(List<String> group) {
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

    public DataUtil(List<String> taskTodo, List<String> taskDone) {
        this.taskTodo = taskTodo;
        this.taskDone = taskDone;
    }

    public List<String> getTaskTodo() {
        return taskTodo;
    }

    public void setTaskTodo(List<String> taskTodo) {
        this.taskTodo = taskTodo;
    }

    public List<String> getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(List<String> taskDone) {
        this.taskDone = taskDone;
    }
}
