package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {
    private long id;
    private boolean isCompleted;
    private String title;

    public long getId() {
        return id;
    }

    public TaskModel setId(long id) {
        this.id = id;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public TaskModel setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TaskModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.isCompleted = source.readByte() != 0;
        this.title = source.readString();
    }

    public TaskModel() {
    }

    protected TaskModel(Parcel in) {
        this.id = in.readLong();
        this.isCompleted = in.readByte() != 0;
        this.title = in.readString();
    }

    public static final Parcelable.Creator<TaskModel> CREATOR = new Parcelable.Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel source) {
            return new TaskModel(source);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };
}
