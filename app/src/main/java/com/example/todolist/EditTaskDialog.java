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

import java.security.PrivilegedActionException;

public class EditTaskDialog extends DialogFragment {
    private EditTaskCallback callback;
    private String title;
    private TaskModel taskModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (EditTaskCallback) context;
        title = getArguments().getString("title", "");
        taskModel = getArguments().getParcelable("task");
        if (taskModel == null) {
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null, false);
        TextInputEditText titleEt = view.findViewById(R.id.et_dialog_edit);
        TextInputLayout inputLayout = view.findViewById(R.id.etl_dialog_edit);
        View saveBtn = view.findViewById(R.id.btn_dialog_edit);
        titleEt.setText(taskModel.getTitle());
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEt.length() > 0) {
                    taskModel.setTitle(titleEt.getText().toString());
//                    taskModel.setCompleted(false);
                    callback.onEditTask(taskModel);
                    dismiss();
                } else {
                    inputLayout.setError("Can not be Empty");
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public interface EditTaskCallback {
        void onEditTask(TaskModel taskModel);
    }
}
