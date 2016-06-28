package com.honghaisen.todo;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDo extends Fragment {

    private TextView mTitle;
    private Button edit;
    private String title;
    private String id;

    public ToDo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        mTitle = (TextView)view.findViewById(R.id.ToDo);
        edit = (Button)view.findViewById(R.id.edit);
        mTitle.setText(title);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEdit = new Intent(getActivity(), AddActivity.class);
                toEdit.putExtra("edit", true);
                toEdit.putExtra("id", id);
                getActivity().startActivity(toEdit);
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }
}
