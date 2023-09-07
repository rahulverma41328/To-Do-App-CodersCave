package com.example.todoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.room.Room;

import com.example.todoapp.Database.ToDoDatabase;
import com.example.todoapp.Database.UserDao;

public class TaskDialog extends AppCompatDialogFragment {
    private EditText task;
    private EditText task_description;
    private ExampleDialogListner listner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_to_do,null);

        task = view.findViewById(R.id.task);
        task_description = view.findViewById(R.id.task_description);
        builder.setView(view)
                .setTitle("New Task")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String _task = task.getText().toString();
                        String _description = task_description.getText().toString();
                        listner.applyText(_task,_description);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner = (ExampleDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement ExampleDialogListner");
        }
    }

    public interface ExampleDialogListner{
        void applyText(String task,String task_description);
    }
}
