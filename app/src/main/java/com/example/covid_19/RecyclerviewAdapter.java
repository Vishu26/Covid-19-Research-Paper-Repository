package com.example.covid_19;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Task> taskList;

    RecyclerviewAdapter(Context context){
        mContext = context;
        taskList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskName.setText(task.getName());
        holder.tvTaskDesc.setText(task.getDesc());
        holder.details.setText(task.getDesc());
        holder.Taskdet.setText(task.getName());
        holder.date.setText(task.getDate());

    }

    @Override
    public int getItemCount() {
        return taskList!=null?taskList.size():0;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTaskName;
        public TextView tvTaskDesc;
        public TextView details;
        public TextView Taskdet;
        public TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.task_name);
            tvTaskDesc = itemView.findViewById(R.id.task_desc);
            details = itemView.findViewById(R.id.task_desc_exp);
            Taskdet = itemView.findViewById(R.id.task_name_exp);
            date = itemView.findViewById(R.id.date);

        }
    }
}