package com.example.taskmanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.taskmanager.DataUtil;
import com.example.taskmanager.R;

import java.util.List;

public class TaskDetailRecyclerviewAdapter extends RecyclerView.Adapter
        <TaskDetailRecyclerviewAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private List<String> mListTodo;
    private List<String> mListDone;
    private static final int UPDATE_TEXT = 1;  //传递TODO的消息，更新上部的UI
    private static final int UPDATE_DONE = 2;  //传递DONE的消息，更新下部的UI
    private OnItemClickListener onItemClickListener;
    private RecyclerView.Adapter adapter; //传进来的adapter
    private int flag; //标识为上部还是下部 0为上部  1为下部
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int position;
            switch (msg.what){
                case UPDATE_TEXT:
                    position = msg.arg1;
                    removeData(position);
                    DataUtil.dataUtilInstance.setTaskTodo(mListTodo);
                    Log.d("TODO",mListTodo+"");
                    break;
                case UPDATE_DONE:
                    position = msg.arg1;
                    removeData(position);
                    DataUtil.dataUtilInstance.setTaskDone(mListDone);
                    Log.d("TODO",mListDone+"");
                    break;
                default:
                    break;
            }
        }
    };



    public TaskDetailRecyclerviewAdapter(Context context, List<String> mListTodo
            , List<String> mListDone, int flag, RecyclerView.Adapter adapter) {
        this.context = context;
        this.mListTodo = mListTodo;
        this.mListDone = mListDone;
        this.flag = flag;
        this.adapter = adapter;

        DataUtil.dataUtilInstance.setTaskDone(mListDone);
        DataUtil.dataUtilInstance.setTaskTodo(mListTodo);
    }

    public void removeData(int position){
        if(flag == 0) {
            mListTodo.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mListTodo.size());
            //if(adapter != null)
            //adapter.notifyDataSetChanged();
        }else if(flag == 1){
            mListDone.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mListDone.size());
            if(adapter != null)
                adapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_taskdetail_recyclerview,
                viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnLongClickListener(this);
        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.itemView.setTag(position);
        if(flag == 1){
            myViewHolder.checkBox.setChecked(true);
            myViewHolder.taskDetail.setText(mListDone.get(position));
            myViewHolder.taskDetail.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else if(flag == 0){
            myViewHolder.taskDetail.setText(mListTodo.get(position));
            myViewHolder.taskDetail.getPaint().setFlags(0);
            myViewHolder.checkBox.setChecked(false);
        }

        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myViewHolder.checkBox.isChecked()){
                    myViewHolder.taskDetail.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    //myViewHolder.checkBox.setChecked(true);
                    notifyDataSetChanged();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mListDone.add(mListTodo.get(position));
                                DataUtil.dataUtilInstance.setTaskDone(mListDone);
                                Log.d("Done",mListDone+"");
                                Message message = new Message();
                                message.what = UPDATE_TEXT;
                                message.arg1 = position;
                                message.arg2 = mListTodo.size();
                                Thread.sleep(1000);
                                handler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else if (!myViewHolder.checkBox.isChecked()){
                    myViewHolder.taskDetail.getPaint().setFlags(0);
                    //myViewHolder.checkBox.setChecked(false);
                    notifyDataSetChanged();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mListTodo.add(mListDone.get(position));
                                DataUtil.dataUtilInstance.setTaskTodo(mListTodo);
                                Log.d("Done",mListTodo+"");
                                Message message = new Message();
                                message.what = UPDATE_DONE;
                                message.arg1 = position;
                                message.arg2 = mListDone.size();
                                Thread.sleep(1000);
                                handler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(flag == 0) {
            return mListTodo.size();
        }else if(flag == 1){
            return mListDone.size();
        }
        return 0;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemLongClick(view, (int) view.getTag());
        }
        return true;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskDetail;
        CheckBox checkBox;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDetail = (TextView)itemView.findViewById(R.id.task_detail_summary);
            checkBox = (CheckBox)itemView.findViewById(R.id.task_checkbox);
        }
    }

}
