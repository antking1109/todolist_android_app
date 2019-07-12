package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.Adapter.ToDoListAdapter;
import com.example.todolist.Bean.ToDoList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ToDoListDbHelper dbHelper;
    ToDoListAdapter toDoListAdapter;
    ListView listViewToDoList;
    ArrayList<ToDoList> toDoLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDataToListView();

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText txtInput = (EditText)findViewById(R.id.txtInput);
                String inputContent = txtInput.getText().toString();
                ToDoList toDoList = new ToDoList();
                toDoList.set_content(inputContent);

                dbHelper.insertProduct(toDoList);
                txtInput.setText("");
                loadDataToListView();
            }
        });
    }
    private void loadDataToListView(){
        dbHelper = new ToDoListDbHelper(this);
        toDoLists = new ArrayList<>();
        toDoLists = dbHelper.getAllToDoLists();
        toDoListAdapter = new ToDoListAdapter(toDoLists);
        listViewToDoList = findViewById(R.id.lstViewToDo);
        listViewToDoList.setAdapter(toDoListAdapter);
    }
}
