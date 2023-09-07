package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.Database.ToDoDatabase;
import com.example.todoapp.Database.UserDao;

public class task_description extends AppCompatActivity {

    EditText itemTask,itemDescription;
    TextView saveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);
        itemTask = findViewById(R.id.task);
        itemDescription = findViewById(R.id.description);
        saveTask = findViewById(R.id.save_task);

        String _task = getIntent().getStringExtra("task");
        String _desc = getIntent().getStringExtra("description");

        itemTask.setText(_task);
        itemDescription.setText(_desc);

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoDatabase db = Room.databaseBuilder(getApplicationContext(), ToDoDatabase.class,
                        "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                int Uid = (userDao.getUidByNames(_task,_desc));
                userDao.updateById(itemTask.getText().toString(),itemDescription.getText().toString(),Uid);
                Toast.makeText(getApplicationContext(),"Task Updated",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}