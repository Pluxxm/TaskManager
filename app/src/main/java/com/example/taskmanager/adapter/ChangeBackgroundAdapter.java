package com.example.taskmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.taskmanager.R;

import java.util.List;

public class ChangeBackgroundAdapter extends RecyclerView.Adapter
        <ChangeBackgroundAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Integer> background_ids;
    private List<Boolean> isSelected;
    private OnItemClickListener onItemClickListener;

    public ChangeBackgroundAdapter(Context context, List<Integer> background_ids, List<Boolean> isSelected) {
        this.context = context;
        this.background_ids = background_ids;
        this.isSelected = isSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_change_background, viewGroup,false);
        holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.item_change.setTag(i);
        viewHolder.background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .load(background_ids.get(i))
                .placeholder(R.drawable.icon_load_before)
                .error(R.drawable.icon_load_fail)
                .centerCrop()
                .into(viewHolder.background);
        if (!isSelected.get(i)) {
            viewHolder.selectedBack.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.selectedBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return background_ids.size();
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

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView background;
        ImageView selectedBack;
        RelativeLayout item_change;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.item_change_background);
            selectedBack = itemView.findViewById(R.id.item_selected_background);
            item_change = itemView.findViewById(R.id.item_change);
        }
    }
}
