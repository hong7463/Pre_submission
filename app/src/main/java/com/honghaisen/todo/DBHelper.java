package com.honghaisen.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hison7463 on 6/15/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Lists.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Lists (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text NOT NULL," +
                "note text," +
                "date text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Lists");
        onCreate(db);
    }

    public boolean insert(String title, String note, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("title", title);
        contents.put("note", note);
        contents.put("date", date);
        long before = db.rawQuery("SELECT * FROM Lists", null).getCount();
        long after = db.insert("Lists", null, contents);

        if(after - before == 1) {
            return true;
        }
        return false;
    }

    public Cursor getItem(String id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Lists WHERE id = ?", new String[]{id});
    }

    public long getLastId() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT last_insert_rowid()", null);
        res.moveToFirst();
        return res.getInt(0);
    }

    public Cursor getAll() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Lists", null);
    }

    public void update(String id, String title, String note, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("title", title);
        contents.put("note", note);
        contents.put("date", date);
        db.update("Lists", contents, "id = ?", new String[]{id});
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Lists", "id = ?", new String[]{id});
    }
}
