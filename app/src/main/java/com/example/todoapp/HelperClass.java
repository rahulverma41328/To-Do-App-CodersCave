package com.example.todoapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HelperClass {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "heading")
    public String heading;
    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "checkTask")
    public boolean checkTask;

    public HelperClass(String heading, String description,boolean checkTask) {
        this.heading = heading;
        this.description = description;
        this.checkTask = checkTask;
    }

    public boolean isCheckTask() {
        return checkTask;
    }

    public void setCheckTask(boolean checkTask) {
        this.checkTask = checkTask;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
