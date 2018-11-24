package com.example.taskmanager.bean;

public class UserBean {
    //用户的头像与名称
    int avatar;
    String name;
    String mail;

    public UserBean(int avatar, String name, String mail) {
        this.avatar = avatar;
        this.name = name;
        this.mail = mail;
    }

    public UserBean(int avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
