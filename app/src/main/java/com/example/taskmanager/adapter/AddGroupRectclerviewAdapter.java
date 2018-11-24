package com.example.taskmanager.adapter;

import android.content.Context;
import android.graphics.Color;
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

import circletextimage.viviant.com.circletextimagelib.view.CircleTextImage;

public class AddGroupRectclerviewAdapter extends RecyclerView.Adapter<AddGroupRectclerviewAdapter.MyViewHolder>
        implements View.OnClickListener {
    private Context context;
    private List<UserBean> mList;
    private OnItemClickListener onItemClickListener;

    public AddGroupRectclerviewAdapter(Context context, List<UserBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_addgroup_recyclerview,
                viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);  //设置点击事件
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.itemView.setTag(position);  //用于点击事件
        myViewHolder.avatar.setImageResource(mList.get(position).getAvatar());
        myViewHolder.memberName.setText(mList.get(position).getName());
        if (position == 0){
            myViewHolder.isOwner.setVisibility(View.VISIBLE);
        }
        if(position == mList.size() - 1){
            myViewHolder.memberName.setTextColor(Color.parseColor("#008577"));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView memberName;
        TextView isOwner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.iv_useravatar);
            memberName = (TextView)itemView.findViewById(R.id.tv_username);
            isOwner = (TextView)itemView.findViewById(R.id.tv_isowner);
        }
    }
}
