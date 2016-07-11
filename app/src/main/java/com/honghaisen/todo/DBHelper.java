package com.honghaisen.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.name;
import static com.honghaisen.todo.R.id.date;
import static com.honghaisen.todo.R.id.note;

/**
 * Created by hison7463 on 6/15/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, "Lists.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Lists (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text NOT NULL," +
                "year text NOT NULL," +
                "month text NOT NULL," +
                "day text NOT NULL," +
                "note text," +
                "priority text NOT NULL," +
                "status text NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Lists");
        onCreate(db);
    }

    public boolean insert(String title, String year, String month, String day, String note, String priority, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("title", title);
        contents.put("year", year);
        contents.put("month", month);
        contents.put("day", day);
        contents.put("note", note);
        contents.put("priority", priority);
        contents.put("status", status);
        long before = db.rawQuery("SELECT * FROM Lists", null).getCount();
        long after = db.insert("Lists", null, contents);

        Log.d(TAG, before + ", " + after);

        if(after - before == 1) {
            return true;
        }
        return false;
    }

    public Cursor getItem(String id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Lists WHERE _id = ?", new String[]{id});
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

    public void update(String id, String title, String year, String month, String day, String note, String priority, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put("title", title);
        contents.put("year", year);
        contents.put("month", month);
        contents.put("day", day);
        contents.put("note", note);
        contents.put("priority", priority);
        contents.put("status", status);
        db.update("Lists", contents, "_id = ?", new String[]{id});
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Lists", "_id = ?", new String[]{id});
    }
}
