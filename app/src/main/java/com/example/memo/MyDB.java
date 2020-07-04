package com.example.memo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MyDB {
    private SQLiteDatabase db = null;
    private final String TABLE_NAME = "TableMemo";
    private final String _ID = "_id";
    private final String NAME = "Content";
    private final String PRICE = "price";
    private final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, " + PRICE + " INTEGER )";
    private Context context;

    MyDB(Context context) {
        this.context = context;
    }

    public void openDB() throws SQLException {
        try {
            db = context.openOrCreateDatabase("fruitedb.db", Context.MODE_PRIVATE, null);
            db.execSQL(CREATE_TABLE);
        } catch(Exception e) {
            Toast.makeText(context,"fruitdb.db 已建立",Toast.LENGTH_LONG).show();
        }
    }

    public void append(String name, String price) {
        String INSERT_TEXT = "INSERT INTO "+TABLE_NAME+"( "+NAME+","+PRICE+") values ('"+name+"',"+price+")";
        db.execSQL(INSERT_TEXT);
    }

    public void update(String name, String price, long id) {
        String update_text="UPDATE "+TABLE_NAME+" SET "+NAME+"='"+name+"',"+PRICE+"="+price+" WHERE "+_ID+"="+id;
        db.execSQL(update_text);
    }

    public void delete(long id) {
        String DELETE_TEXT = "DELETE FROM " + TABLE_NAME + " WHERE " + _ID + "=" + id;
        db.execSQL(DELETE_TEXT);
    }

    public Cursor select(long id){
        String select_text = "SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + "=" + id;
        Cursor cursor = db.rawQuery(select_text,null);
        return cursor;
    }

    public Cursor select_all(){
        String select_text = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(select_text,null);
        return cursor;
    }
}
