package com.example.taskmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.bean.TaskGroupBean;

import java.util.List;

public class TaskRecyclerviewAdapter extends RecyclerView.Adapter<TaskRecyclerviewAdapter.MyViewHolder>
        implements View.OnLongClickListener, View.OnClickListener {

    private List<TaskGroupBean> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public TaskRecyclerviewAdapter(List<TaskGroupBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        Log.d("ADAPTER",""+mList.size());
    }

    public void removeData(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_task_recyclerview, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.itemView.setTag(position);
        myViewHolder.tv.setText(mList.get(position).getName());
        myViewHolder.iv.setImageResource(mList.get(position).getIcon());
        if(position == 0){
            myViewHolder.tv.setTextColor(Color.parseColor("#1296DB"));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView)itemView.findViewById(R.id.id_task_icon);
            tv = (TextView)itemView.findViewById(R.id.id_task_name);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(view, (int) view.getTag());
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }
}
