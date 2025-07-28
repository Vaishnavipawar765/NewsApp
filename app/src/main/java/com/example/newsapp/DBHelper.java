package com.example.newsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper( Context context) {
        super(context, "NewsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE News(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS News");
        onCreate(db);

    }
    public boolean insertNews(String title, String content){
        SQLiteDatabase db = this. getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        long res = db.insert("News", null,cv);
        return res !=1;
    }

    public Cursor getAllNews(){
        SQLiteDatabase db= this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM News ", null);
    }
    public boolean deleteNews(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("News", "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    public boolean updateNews(int id, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        int res = db.update("News", cv, "id=?", new String[]{String.valueOf(id)});
        return res > 0;
    }



}
