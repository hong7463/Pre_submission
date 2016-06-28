package com.honghaisen.todo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private Button add;
    private DBHelper dbHelper;
    private FragmentManager fragmentManager;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        add = (Button)findViewById(R.id.add);
        dbHelper = new DBHelper(this);
        fragmentManager = getFragmentManager();
        container = (LinearLayout)findViewById(R.id.container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        container.removeAllViews();
        Cursor res = dbHelper.getAll();
        res.moveToFirst();
        Log.d("num", String.valueOf(res.getCount()));
        while(!res.isAfterLast()) {
            String title = res.getString(res.getColumnIndex("title"));
            String id = res.getString(res.getColumnIndex("id"));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ToDo fragment = new ToDo();
            fragment.setTitle(title);
            fragment.setId(id);
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
            res.moveToNext();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainToAdd = new Intent(TodoActivity.this, AddActivity.class);
                mainToAdd.putExtra("edit", false);
                TodoActivity.this.startActivity(mainToAdd);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
