package com.example.todolist.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.todolist.Bean.ToDoList;
import com.example.todolist.R;
import com.example.todolist.ToDoListDbHelper;
import com.example.todolist.edit_todolist;

import java.sql.SQLException;
import java.util.ArrayList;

public class ToDoListAdapter extends BaseAdapter {
    final ArrayList<ToDoList> toDoLists;
    public ToDoListAdapter(ArrayList<ToDoList> toDoLists){
        this.toDoLists = toDoLists;
    }

    @Override
    public int getCount() {
        return toDoLists.size();
    }

    @Override
    public Object getItem(int i) {
        return toDoLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return toDoLists.get(i).get_id();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View viewToDoList;
        if (view == null){
            viewToDoList = View.inflate(viewGroup.getContext(), R.layout.list_view_to_do, null);
        }else{
            viewToDoList = view;
        }
        //Bind sữ liệu phần tử vào View
        final ToDoList toDoList = (ToDoList) getItem(i);
        ((TextView) viewToDoList.findViewById(R.id.vtxtContent)).setText(toDoList.get_content());

        Button btnDelete = (Button)viewToDoList.findViewById(R.id.btnDelete);
        Button btnEdit = (Button)viewToDoList.findViewById(R.id.btnEdit);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoListDbHelper toDoListDbHelper = new ToDoListDbHelper(view.getContext());
                toDoListDbHelper.deleteToDoListByID(((int) getItemId(i)));
                toDoLists.remove(i);
                notifyDataSetChanged();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldText = ((ToDoList) getItem(i)).get_content();
                Intent intentEdit = new Intent(view.getContext(), edit_todolist.class);
                intentEdit.putExtra("oldText",oldText);
                intentEdit.putExtra("oldID",((ToDoList) getItem(i)).get_id());
                view.getContext().startActivity(intentEdit);
            }
        });
        return viewToDoList;
    }
}
