package com.example.taskmanager.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 76952 on 2018/11/24.
 */

public class BaseHttpModel extends BaseModel {
    @SerializedName("message")
    private String messageText;
    @SerializedName("errorCode")
    private String codeText;
    public String getMessageText(){
        return messageText;
    }
    public String getCodeText() {
        return codeText;
    }
    public void setMessageText(String str) {
        this.messageText = str;
    }
    public void setCodeText(String str) {
        this.codeText = str;
    }
}
