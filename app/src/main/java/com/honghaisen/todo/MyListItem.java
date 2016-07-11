package com.honghaisen.todo;

import android.database.Cursor;

/**
 * Created by hison7463 on 7/10/16.
 */

public class MyListItem {

    private String listTitle;
    private String listPriority;

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public void setListPriority(String listPriority) {
        this.listPriority = listPriority;
    }

    public String getListPriority() {
        return listPriority;
    }

    public String getListTitle() {
        return listTitle;
    }

    public static MyListItem fromCursor(Cursor cursor) {
        MyListItem myListItem = new MyListItem();
        myListItem.setListPriority(cursor.getString(cursor.getColumnIndex("priority")));
        myListItem.setListTitle(cursor.getString(cursor.getColumnIndex("title")));
        return myListItem;
    }
}
