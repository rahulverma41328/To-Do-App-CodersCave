package com.example.todoapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapp.HelperClass;

@Database(entities = {HelperClass.class},version = 1)
public abstract class ToDoDatabase extends RoomDatabase{
    public abstract UserDao userDao();
}
