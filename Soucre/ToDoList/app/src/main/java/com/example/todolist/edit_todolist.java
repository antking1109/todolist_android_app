package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolist.Bean.ToDoList;

public class edit_todolist extends AppCompatActivity {
    ToDoListDbHelper toDoListDbHelper = new ToDoListDbHelper(this);
    ToDoList toDoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todolist);

        Button btnSave = (Button)findViewById(R.id.btnSave);
        Button btnClean = (Button)findViewById(R.id.btnClean);
        Intent intent = this.getIntent();
        final EditText txtContent = (EditText)findViewById(R.id.txtEdit);
        final int id = intent.getIntExtra("oldID",-1);
        txtContent.setText(intent.getStringExtra("oldText"));
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtContent.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDoList = new ToDoList();
                toDoList.set_content(txtContent.getText().toString());
                toDoList.set_id(id);
                toDoListDbHelper.update(toDoList);
                Intent intent = new Intent(view.getContext(),MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
