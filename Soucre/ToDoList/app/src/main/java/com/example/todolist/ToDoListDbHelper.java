package com.example.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todolist.Bean.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class ToDoListDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "ToDoListDbHelper";
    private static final String DATABASE_NAME = "mytodolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "todolist";

    public ToDoListDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT NOT NULL" +
                ")";

        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
    void insertProduct(ToDoList toDoList) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL("INSERT INTO todolist (content) VALUES (?)",
                    new String[]{toDoList.get_content()});
            Log.i(TAG, "Insert done");
        }catch (SQLException ex){
            Log.i(TAG, "Error: " + ex);
        }
    }

    public ArrayList<ToDoList> getAllToDoLists() {

        ArrayList<ToDoList> toDoLists = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, content FROM todolist ORDER BY id DESC", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int todolist_id = cursor.getInt(0);
            String todolist_content = cursor.getString(1);
            ToDoList tdl = new ToDoList();
            tdl.set_id(todolist_id);
            tdl.set_content(todolist_content);
            toDoLists.add(tdl);
            cursor.moveToNext();
        }

        cursor.close();

        return toDoLists;
    }
    public void deleteToDoListByID(int id) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL("DELETE FROM todolist WHERE id = ?", new String[]{String.valueOf(id)});
        }catch (SQLException ex){
            Log.i(TAG,"ERROR DELETE: " + ex);
        }
    }
    public void update(ToDoList toDoList) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE todolist SET content=? where id = ?",
                new String[]{toDoList.get_content(), String.valueOf(toDoList.get_id())});
    }
}
