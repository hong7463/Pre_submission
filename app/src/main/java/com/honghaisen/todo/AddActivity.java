package com.honghaisen.todo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    private EditText title;
    private EditText note;
    private EditText date;
    private Button button;
    private boolean edit;
    private DBHelper dbHelper;
    private boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title = (EditText) findViewById(R.id.title);
        note = (EditText) findViewById(R.id.note);
        date = (EditText) findViewById(R.id.date);
        button = (Button) findViewById(R.id.button);
        dbHelper = new DBHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        edit = (boolean) getIntent().getExtras().get("edit");
        if (edit) {
            String id = getIntent().getExtras().getString("id");
            Cursor res = dbHelper.getItem(id);
            if (res.moveToFirst()) {
                title.setEnabled(false);
                note.setEnabled(false);
                date.setEnabled(false);
                title.setText(res.getString(res.getColumnIndex("title")));
                note.setText(res.getString(res.getColumnIndex("note")));
                date.setText(res.getString(res.getColumnIndex("date")));
                button.setText("Edit");
            }
        } else {
            button.setText("Add");
        }
        edit = (boolean) getIntent().getExtras().get("edit");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit) {
                    button.setText("Done");
                    editing = true;
                    edit = false;
                    title.setEnabled(true);
                    note.setEnabled(true);
                    date.setEnabled(true);
                } else if (editing) {
                    button.setText("Edit");
                    editing = false;
                    edit = true;
                    title.setEnabled(false);
                    note.setEnabled(false);
                    date.setEnabled(false);
                    dbHelper.update(getIntent().getExtras().getString("id"), title.getText().toString(), note.getText().toString(), date.getText().toString());
                } else {
                    dbHelper.insert(title.getText().toString(), note.getText().toString(), date.getText().toString());
                    AddActivity.this.startActivity(new Intent(AddActivity.this, TodoActivity.class));
                    AddActivity.this.finish();
                }
            }
        });
    }
}
