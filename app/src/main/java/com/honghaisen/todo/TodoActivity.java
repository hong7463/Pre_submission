package com.honghaisen.todo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.support.v4.app.DialogFragment.STYLE_NO_TITLE;

public class TodoActivity extends AppCompatActivity implements AddDialogFragment.DateBridge{

    private final static String TAG = TodoActivity.class.getSimpleName();

    private Button add;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private MyListCursorAdapter myListCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        add = (Button)findViewById(R.id.add);
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        Cursor cursor = dbHelper.getAll();
        myListCursorAdapter = new MyListCursorAdapter(TodoActivity.this, cursor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myListCursorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment = new AddDialogFragment();
                addDialogFragment.setStyle(STYLE_NO_TITLE, 0);;
                addDialogFragment.show(getSupportFragmentManager(), "insert");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateView() {
        myListCursorAdapter.changeCursor(dbHelper.getAll());
    }
}
