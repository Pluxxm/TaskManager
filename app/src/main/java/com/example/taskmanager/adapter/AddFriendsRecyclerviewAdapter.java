package com.example.taskmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.bean.UserBean;

import java.util.List;

public class AddFriendsRecyclerviewAdapter extends RecyclerView.Adapter<AddFriendsRecyclerviewAdapter.MyViewHolder> {
    private Context context;
    private List<UserBean> userBeanList; //任务中已有的用户

    public AddFriendsRecyclerviewAdapter(Context context, List<UserBean> userBeanList) {
        this.context = context;
        this.userBeanList = userBeanList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_addfriends_recyclerview,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.iv_addfriends_avatar.setImageResource(userBeanList.get(i).getAvatar());
        myViewHolder.tv_name.setText(userBeanList.get(i).getName());
        myViewHolder.subtitle_mail.setText(userBeanList.get(i).getMail());
        if(i == 0){
            myViewHolder.tv_flag.setText("所有者");
        }else{
            myViewHolder.tv_flag.setText("已添加");
        }
    }

    @Override
    public int getItemCount() {
        return userBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_addfriends_avatar;
        TextView tv_name;
        TextView subtitle_mail;
        TextView tv_flag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_addfriends_avatar = itemView.findViewById(R.id.iv_addfriends_avatar);
            tv_name = itemView.findViewById(R.id.name);
            subtitle_mail = itemView.findViewById(R.id.subtitle_mail);
            tv_flag = itemView.findViewById(R.id.tv_flag);
        }
    }
}
