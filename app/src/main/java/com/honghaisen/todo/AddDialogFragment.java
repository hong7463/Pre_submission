package com.honghaisen.todo;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static com.honghaisen.todo.R.id.date;
import static com.honghaisen.todo.R.id.submit;

/**
 * Created by hison7463 on 7/5/16.
 */

public class AddDialogFragment extends DialogFragment {

    private static final String TAG = AddDialogFragment.class.getSimpleName();

    private TextView taskName;
    private NumberPicker year;
    private NumberPicker month;
    private NumberPicker day;
    private TextView note;
    private Spinner priority;
    private Spinner status;
    private Button submit;
    private Button cancel;
    private DBHelper dbHelper;

    private String mTitle;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mNote;
    private String mPriority;
    private String mStatus;
    private String mId;

    private Map<String, Integer> priorityMap;
    private Map<String, Integer> statusMap;

    public AddDialogFragment() {
        super();
        priorityMap = new HashMap<String, Integer>();
        statusMap = new HashMap<String, Integer>();
        initMap();
    }

    private void initMap() {
        priorityMap.put("High", 0);
        priorityMap.put("Regular", 1);
        priorityMap.put("Low", 2);

        statusMap.put("To Do", 0);
        statusMap.put("Done", 1);
    }

    public interface DateBridge {
        public void updateView();
    }

    public void setmId(String id) {
        this.mId = id;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public void setmPriority(String mPriority) {
        this.mPriority = mPriority;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        Log.d(TAG, getTag());
        initListener();
        setNumberPicker();
        if(getTag().equals("update")) {
            Log.d(TAG, "initView");
            initView(view);
        }
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int)(size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        dbHelper = new DBHelper(getActivity());



        super.onResume();
    }

    private void initView(View view) {
        taskName.setText(mTitle);
        year.setValue(mYear);
        month.setValue(mMonth);
        day.setValue(mDay);
        note.setText(mNote);
        priority.setSelection(priorityMap.get(mPriority));
        status.setSelection(statusMap.get(mStatus));
    }

    private void setNumberPicker() {
        year.setMinValue(2016);
        year.setMaxValue(2030);
        month.setMinValue(1);
        month.setMaxValue(12);
        day.setMinValue(1);
        day.setMaxValue(31);
    }

    private void initListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, priority.getSelectedItem().toString());
                if(getTag().equals("insert")) {
                    dbHelper.insert(taskName.getText().toString(), String.valueOf(year.getValue()),
                            String.valueOf(month.getValue()), String.valueOf(day.getValue()), note.getText().toString(),
                            priority.getSelectedItem().toString(), status.getSelectedItem().toString());
                }
                else if(getTag().equals("update")) {
                    dbHelper.update(mId, taskName.getText().toString(), String.valueOf(year.getValue()),
                            String.valueOf(month.getValue()), String.valueOf(day.getValue()), note.getText().toString(),
                            priority.getSelectedItem().toString(), status.getSelectedItem().toString());
                }
                DateBridge dateBridge = (DateBridge) getActivity();
                dateBridge.updateView();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void bindView(View view) {
        taskName = (TextView) view.findViewById(R.id.taskName);
        year = (NumberPicker) view.findViewById(R.id.year);
        month = (NumberPicker) view.findViewById(R.id.month);
        day = (NumberPicker) view.findViewById(date);
        note = (TextView) view.findViewById(R.id.note);
        priority = (Spinner) view.findViewById(R.id.priority);
        status = (Spinner) view.findViewById(R.id.status);
        submit = (Button) view.findViewById(R.id.submit);
        cancel = (Button) view.findViewById(R.id.cancel);
    }
}
