package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddTaskDialog extends DialogFragment {
    private AddNewTaskCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (AddNewTaskCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null, false);
        TextInputEditText titleEt = view.findViewById(R.id.et_dialog_save);
        TextInputLayout inputLayout = view.findViewById(R.id.etl_dialog_save);
        View saveBtn = view.findViewById(R.id.btn_dialog_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEt.length() > 0) {
                    TaskModel taskModel = new TaskModel();
                    taskModel.setTitle(titleEt.getText().toString());
                    taskModel.setCompleted(false);
                    callback.onNewTask(taskModel);
                    dismiss();
                } else {
                    inputLayout.setError("Can not be Empty");
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public interface AddNewTaskCallback {
        void onNewTask(TaskModel taskModel);
    }
}
