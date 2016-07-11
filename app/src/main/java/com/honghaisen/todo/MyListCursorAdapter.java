package com.honghaisen.todo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;


/**
 * Created by hison7463 on 7/10/16.
 */

public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> {

    private final static String TAG = MyListCursorAdapter.class.getSimpleName();

    private Context mContext;
    private DBHelper dbHelper;

    public MyListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView listTitle;
        public TextView listPriority;

        public ViewHolder(View itemView) {
            super(itemView);
            listTitle = (TextView) itemView.findViewById(R.id.list_title);
            listPriority = (TextView) itemView.findViewById(R.id.list_priority);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "ItemClick: " + listTitle.getText().toString());
            Log.d(TAG,  MyListCursorAdapter.this.getItemId(getAdapterPosition()) + "");
            Cursor cursor = dbHelper.getItem(String.valueOf(MyListCursorAdapter.this.getItemId(getAdapterPosition())));
            cursor.moveToFirst();
            AddDialogFragment addDialogFragment = new AddDialogFragment();
            addDialogFragment.setmTitle(cursor.getString(cursor.getColumnIndex("title")));
            addDialogFragment.setmYear(Integer.valueOf(cursor.getString(cursor.getColumnIndex("year"))));
            addDialogFragment.setmMonth(Integer.valueOf(cursor.getString(cursor.getColumnIndex("month"))));
            addDialogFragment.setmDay(Integer.valueOf(cursor.getString(cursor.getColumnIndex("day"))));
            addDialogFragment.setmNote(cursor.getString(cursor.getColumnIndex("note")));
            addDialogFragment.setmPriority(cursor.getString(cursor.getColumnIndex("priority")));
            addDialogFragment.setmStatus(cursor.getString(cursor.getColumnIndex("status")));
            addDialogFragment.setmId(String.valueOf(MyListCursorAdapter.this.getItemId(getAdapterPosition())));
            FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            addDialogFragment.show(fragmentManager, "update");
        }

        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(mContext)
                    .setTitle("Delete the entry?")
                    .setMessage("Do you really want to delete this entry?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbHelper.delete(String.valueOf(MyListCursorAdapter.this.getItemId(getAdapterPosition())));
                            ((AddDialogFragment.DateBridge) mContext).updateView();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        MyListItem myListItem = MyListItem.fromCursor(cursor);
        viewHolder.listTitle.setText(myListItem.getListTitle());
        viewHolder.listPriority.setText(myListItem.getListPriority());
    }

}
