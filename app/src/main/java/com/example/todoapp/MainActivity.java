package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.example.todoapp.Database.ToDoDatabase;
import com.example.todoapp.Database.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDialog.ExampleDialogListner {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.to_do_view);
        floatingActionButton = findViewById(R.id.add_to_do);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        ToDoRecyclerView();
    }

    public void openDialog() {
        TaskDialog taskDialog = new TaskDialog();
        taskDialog.show(getSupportFragmentManager(),"example Dialog");

    }

    private void ToDoRecyclerView() {
        ToDoDatabase db = Room.databaseBuilder(getApplicationContext(),ToDoDatabase.class,
              "room_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        List<HelperClass> helperClasses = userDao.getAllData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ToDoAdapter adapter = new ToDoAdapter(helperClasses,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void applyText(String task, String task_description) {
       ToDoDatabase db = Room.databaseBuilder(getApplicationContext(),ToDoDatabase.class,
               "room_db").allowMainThreadQueries().build();
       UserDao userDao = db.userDao();
       userDao.insertRecord(new HelperClass(task,task_description,false));

        List<HelperClass> helperClasses = userDao.getAllData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ToDoAdapter adapter = new ToDoAdapter(helperClasses,this);
        recyclerView.setAdapter(adapter);
    }
}