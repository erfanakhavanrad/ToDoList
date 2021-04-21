package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddNewTaskCallback, TaskAdapter.TaskItemEventListener,
        EditTaskDialog.EditTaskCallback {
    private static final String TAG = "mainPage";
    TaskAdapter taskAdapter = new TaskAdapter(this);
    RecyclerView recyclerView;
    private SQLiteHelper sqLiteHelper;
    ImageView iv_main_clearTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_main_clearTasks = findViewById(R.id.iv_main_clearTasks);
        recyclerView = findViewById(R.id.rv_main_tasks);
        sqLiteHelper = new SQLiteHelper(this);
//        SqlScoutServer.create(this, getPackageName());


        TaskModel taskModel = new TaskModel();
        taskModel.setTitle("fsfsesf");
        taskModel.setCompleted(false);
        long result = sqLiteHelper.addTask(taskModel);
        Log.i(TAG, "onCreate: " + result);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);


        List<TaskModel> taskModels = sqLiteHelper.getTask();
        taskAdapter.addItems(taskModels);

        View addNewTaskFab = findViewById(R.id.fab_main_addNewTask);
        addNewTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialog dialog = new AddTaskDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });

        iv_main_clearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.clearAllTasks();
                taskAdapter.clearItems();
            }
        });

    }

    @Override
    public void onNewTask(TaskModel taskModel) {
        long taskID = sqLiteHelper.addTask(taskModel);
        if (taskID != -1) {
            taskModel.setId(taskID);
            taskAdapter.addItem(taskModel);
        } else {
            Log.e(TAG, "onNewTask: Item not inserted.");
        }
    }

    @Override
    public void onDeleteButtonClick(TaskModel taskModel) {
        int result = sqLiteHelper.deleteTask(taskModel);
        if (result > 0) {
            taskAdapter.deleteItem(taskModel);
        }
    }

    @Override
    public void onItemLongPress(TaskModel taskModel) {
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", taskModel);
        editTaskDialog.setArguments(bundle);
        editTaskDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onEditTask(TaskModel taskModel) {
        int result = sqLiteHelper.updateTask(taskModel);
        if (result > 0) {
            taskAdapter.updateItem(taskModel);
        }
    }
}