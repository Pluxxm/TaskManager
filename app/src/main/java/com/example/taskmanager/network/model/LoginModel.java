package com.example.taskmanager.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 76952 on 2018/11/24.
 */

public class LoginModel extends BaseModel {
    // Gson 解析的时候是根据字段名来匹配的, 不管是对象转 JSON 还是 JSON 转对象，字段名称会被替换成注解的名字
    @SerializedName("message")
    private String messageText;
    @SerializedName("errorCode")
    private String codeText;

    public class Data {
        @SerializedName("token")
        private String tokenText;
        @SerializedName("userId")
        private String userIdText;
        @SerializedName("isFirstTime")
        private Boolean isFirstTime;
        public String getTokenText(){
            return tokenText;
        }
        public void setTokenText(String str){
            this.tokenText = str;
        }
        public String getUserIdText(){
            return userIdText;
        }
        public void setUserIdText(String str){
            this.userIdText = str;
        }
        public Boolean getIsFirstTime(){ return this.isFirstTime; }
        public void setIsFirstTime(Boolean isFirstTime) {this.isFirstTime = isFirstTime;}
    }

    @SerializedName("data")
    private Data dataText;

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
    public Data getDataText(){
        return dataText;
    }
    public void setDataText(Data d){
        this.dataText = d;
    }
}
