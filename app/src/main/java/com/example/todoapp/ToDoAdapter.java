package com.example.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.todoapp.Database.ToDoDatabase;
import com.example.todoapp.Database.UserDao;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    List<HelperClass> helperClasses;
    private Context context;

    public ToDoAdapter(List<HelperClass> helperClasses, Context context) {
        this.helperClasses = helperClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_card,parent,false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        HelperClass helperClass = helperClasses.get(holder.getAdapterPosition());
        holder.heading.setText(helperClass.getHeading());
        holder.description.setText(helperClass.getDescription());
        holder.checkBox.setChecked(helperClass.isCheckTask());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ToDoDatabase db = Room.databaseBuilder(context,ToDoDatabase.class,
                        "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                int Uid = (userDao.getUidByNames(helperClass.getHeading(),helperClass.getDescription()));
                userDao.updateTaskById(isChecked,Uid);
            }
        });



        holder.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.delete_edit);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id==R.id.delete_task){
                                ToDoDatabase db = Room.databaseBuilder(holder.heading.getContext(),ToDoDatabase.class,
                                        "room_db").allowMainThreadQueries().build();
                                UserDao userDao = db.userDao();
                                userDao.deleteById(helperClasses.get(holder.getAdapterPosition()).getUid());
                                helperClasses.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                return true;
                        }
                        return false;
                    }
                });
            }
        });

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,task_description.class);
                intent.putExtra("task",helperClasses.get(holder.getAdapterPosition()).getHeading());
                intent.putExtra("description",helperClasses.get(holder.getAdapterPosition()).getDescription());
                context.startActivity(intent);

                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

        ToDoDatabase db = Room.databaseBuilder(context,ToDoDatabase.class,"room_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        int Uid = userDao.getUidByNames(helperClasses.get(holder.getAdapterPosition()).
                getHeading(),helperClasses.get(holder.getAdapterPosition()).getDescription());


    }

    @Override
    public int getItemCount() {
        return helperClasses.size();
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder{

        TextView heading,description;
        CheckBox checkBox;
        ImageView menuOption;
        CardView itemCardView;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            description = itemView.findViewById(R.id.note);
            checkBox = itemView.findViewById(R.id.checkBox);
            menuOption = itemView.findViewById(R.id.menu_option);
            itemCardView = itemView.findViewById(R.id.itemCard);
        }
    }
}
