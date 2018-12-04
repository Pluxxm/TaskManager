package com.example.taskmanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.TextView;

import com.example.taskmanager.DataUtil;
import com.example.taskmanager.ItemClickActivity;
import com.example.taskmanager.R;
import com.example.taskmanager.network.apis.ChangeTodoStatus;
import com.example.taskmanager.network.model.BaseHttpModel;
import com.example.taskmanager.network.model.TodoModel;

import java.util.ArrayList;

public class TaskDetailRecyclerviewAdapter extends RecyclerView.Adapter
        <TaskDetailRecyclerviewAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private ArrayList<TodoModel.todoItem> mListTodo;
    private ArrayList<TodoModel.todoItem> mListDone;
    private static final int UPDATE_TEXT = 1;  //传递TODO的消息，更新上部的UI
    private static final int UPDATE_DONE = 2;  //传递DONE的消息，更新下部的UI
    private OnItemClickListener onItemClickListener;
    private RecyclerView.Adapter adapter; //传进来的adapter
    private int flag; //标识为上部还是下部 0为上部  1为下部
    private ItemClickActivity itemClickActivity;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int position;
            switch (msg.what){
                case UPDATE_TEXT:
                    position = msg.arg1;
                    Log.i("position", position+"");
                    Log.i("todoId", DataUtil.dataUtilInstance.getTaskTodo().get(position).getId()+"");
                    changeStatus(DataUtil.dataUtilInstance.getTaskTodo().get(position).getId(),true, position);
                    //removeData(position);
                    Log.d("TODO",mListTodo+"");
                    break;
                case UPDATE_DONE:
                    position = msg.arg1;
                    changeStatus(DataUtil.dataUtilInstance.getTaskDone().get(position).getId(),false, position);
                    //removeData(position);
                    Log.d("TODO",mListDone+"");
                    break;
                default:
                    break;
            }
        }
    };



    public TaskDetailRecyclerviewAdapter(Context context, ArrayList<TodoModel.todoItem> mListTodo
            , ArrayList<TodoModel.todoItem> mListDone, int flag, RecyclerView.Adapter adapter) {
        this.context = context;
        this.mListTodo = mListTodo;
        this.mListDone = mListDone;
        this.flag = flag;
        this.adapter = adapter;
        this.itemClickActivity = (ItemClickActivity)context;

        DataUtil.dataUtilInstance.setTaskDone(mListDone);
        DataUtil.dataUtilInstance.setTaskTodo(mListTodo);
    }

    public void removeData(int position){
        if(flag == 0) {
            mListTodo.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mListTodo.size());
            DataUtil.dataUtilInstance.setTaskTodo(mListTodo);
            //if(adapter != null)
            //adapter.notifyDataSetChanged();
        }else if(flag == 1){
            mListDone.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mListDone.size());
            if(adapter != null)
                adapter.notifyDataSetChanged();
            DataUtil.dataUtilInstance.setTaskDone(mListDone);
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
            myViewHolder.taskDetail.setText(mListDone.get(position).getTitle());
            myViewHolder.taskDetail.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else if(flag == 0){
            myViewHolder.taskDetail.setText(mListTodo.get(position).getTitle());
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
                                Thread.sleep(800);
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

    public void changeStatus(int todoId, boolean status, int position){
        final int finalTodoId = todoId;
        final boolean finalStatus = status;
        final int finalPosition = position;
        //final ArrayList<TodoModel.todoItem> finalList = list;
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpModel changeStatus = ChangeTodoStatus.changeTodoStatus(finalTodoId, finalStatus);
                Log.i("changeStatus", changeStatus.getCodeText());
                Log.i("changeStatus", changeStatus.getMessageText());
                if (changeStatus.getCodeText().equals("000000")){
                    itemClickActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(finalStatus == true) {
                                // todo => done
                                removeData(finalPosition);
                                //DataUtil.dataUtilInstance.setTaskTodo(finalList);
                            } else{
                                // done => todo
                                removeData(finalPosition);
                                //DataUtil.dataUtilInstance.setTaskDone(finalList);
                            }
                        }
                    });

                }
            }
        }).start();
    }
}
