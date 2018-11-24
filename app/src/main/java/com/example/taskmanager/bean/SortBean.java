package com.example.taskmanager.bean;

public class SortBean {
    String task;
    String taskF;

    public SortBean(String task, String taskF) {
        this.task = task;
        this.taskF = taskF;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskF() {
        return taskF;
    }

    public void setTaskF(String taskF) {
        this.taskF = taskF;
    }
}
