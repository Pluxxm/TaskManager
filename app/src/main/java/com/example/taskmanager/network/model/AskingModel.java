package com.example.taskmanager.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 76952 on 2018/12/10.
 */

public class AskingModel extends BaseHttpModel {
    public class Invitation{
        @SerializedName("status")
        private boolean status;
        @SerializedName("creatorName")
        private String creatorName;
        @SerializedName("teamId")
        private int teamId;
        @SerializedName("teamName")
        private String teamName;
        public boolean getStatus(){
            return status;
        }
        public void setStatus(Boolean status){
            this.status = status;
        }
        public String getCreatorName(){
            return creatorName;
        }
        public void setCreatorName(String creatorName){
            this.creatorName = creatorName;
        }
        public int getTeamId(){
            return teamId;
        }
        public void setTeamId(int teamId){
            this.teamId = teamId;
        }
        public String getTeamName(){
            return teamName;
        }
        public void setTeamName(String teamName){
            this.teamName = teamName;
        }
    }
    @SerializedName("data")
    private Invitation data;
    public Invitation getData(){
        return data;
    }
    public void setData(Invitation data){
        this.data = data;
    }
}
