package com.example.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
// @auther: flyotlin on 2020.07.01

// Database
// ItemTouchHelper
public class MainActivity extends AppCompatActivity {

    private EditText toDo;
    private Button addMemo;

    private RecyclerView memoRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> dataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setOnAddMemoClicked();
        setRecyclerView();
    }

    private void initViews() {
        toDo = (EditText) findViewById(R.id.edittext_todo);
        addMemo = (Button) findViewById(R.id.button_addMemo);
        memoRecyclerView = (RecyclerView) findViewById(R.id.memoes);
    }

    private void setOnAddMemoClicked() {
        addMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addMemo(toDo.getText().toString());
                toDo.setText("");
            }
        });
    }

    private void setRecyclerView() {
        memoRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        memoRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(dataset);
        memoRecyclerView.setAdapter(mAdapter);
    }



}