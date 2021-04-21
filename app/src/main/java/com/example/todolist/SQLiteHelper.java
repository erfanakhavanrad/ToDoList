package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TABLE_TASKS = "tbl_tasks";

    public SQLiteHelper(@Nullable Context context) {
        super(context, "db_app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_TASKS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, completed BOOLEAN)");

        } catch (SQLException e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addTask(TaskModel taskModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", taskModel.getTitle());
        contentValues.put("completed", taskModel.isCompleted());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_TASKS, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<TaskModel> getTask() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
        List<TaskModel> taskModelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(cursor.getLong(0));
                taskModel.setTitle(cursor.getString(1));
                taskModel.setCompleted(cursor.getInt(2) == 1);
                taskModelList.add(taskModel);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return taskModelList;
    }

    // TODO: 4/19/2021 learn this
    public int updateTask(TaskModel taskModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", taskModel.getTitle());
        contentValues.put("completed", taskModel.isCompleted());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.update(TABLE_TASKS, contentValues, "id = ?", new String[]{String.valueOf(taskModel.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public void searchInTask(String query) {

    }

    public int deleteTask(TaskModel taskModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_TASKS, "id = ?", new String[]{String.valueOf(taskModel.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public void clearAllTasks() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
         sqLiteDatabase.execSQL("DELETE FROM " + TABLE_TASKS);
    }


}
