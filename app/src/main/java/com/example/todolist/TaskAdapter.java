package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskModel> taskModelList = new ArrayList<>();
    private TaskItemEventListener eventListener;

    public TaskAdapter(TaskItemEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(taskModelList.get(position));
    }

    public void addItem(TaskModel taskModel) {
        taskModelList.add(0, taskModel);
        notifyItemInserted(0);
    }

    public void updateItem(TaskModel taskModel){
        for (int i = 0; i < taskModelList.size(); i++) {
            if (taskModel.getId()== taskModelList.get(i).getId()){
                taskModelList.set(i, taskModel);
                notifyItemChanged(i);
            }
        }
    }

    public void addItems(List<TaskModel> taskModelList) {
        this.taskModelList.addAll(taskModelList);
        notifyDataSetChanged();
    }

    public void deleteItem(TaskModel taskModel) {
        for (int i = 0; i < taskModelList.size(); i++) {
            if (taskModelList.get(i).getId() == taskModel.getId()) {
                taskModelList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
    public void clearItems(){
        this.taskModelList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private View deleteBtn;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox_task);
            deleteBtn = itemView.findViewById(R.id.btn_task_delete);
        }

        public void bindTask(TaskModel taskModel) {
            checkBox.setChecked(taskModel.isCompleted());
            checkBox.setText(taskModel.getTitle());
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onDeleteButtonClick(taskModel);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    eventListener.onItemLongPress(taskModel);
                    return false;
                }
            });

        }


    }

    public interface TaskItemEventListener {
        void onDeleteButtonClick(TaskModel taskModel);
        void onItemLongPress(TaskModel taskModel);
    }

}
