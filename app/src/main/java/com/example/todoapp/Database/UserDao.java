package com.example.todoapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapp.HelperClass;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertRecord(HelperClass helperClass);

    @Query("SELECT * FROM HelperClass")
    List<HelperClass> getAllData();

    @Query("DELETE FROM HelperClass WHERE Uid= :id")
    void deleteById(int id);

    @Query("UPDATE HelperClass SET heading= :firstName, description= :lastName WHERE uid = :id")
    void updateById(String firstName,String lastName,int id);

    @Query("SELECT uid FROM HelperClass WHERE heading = :firstName AND description = :lastName")
    int getUidByNames(String firstName, String lastName);

    @Update
    void updateUser(HelperClass user);

    @Query("UPDATE helperclass SET checkTask= :isChecked WHERE uid= :id")
    void updateTaskById(boolean isChecked,int id);
}
