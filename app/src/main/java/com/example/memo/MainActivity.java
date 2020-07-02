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

import java.util.ArrayList;
// @auther: flyotlin on 2020.07.01

// More function on each memo
// Database
// ItemTouchHelper
public class MainActivity extends AppCompatActivity {

    private EditText toDo;
    private Button addMemo;

    private RecyclerView memoRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] dataset = {"ABCDDD", "2216sdf", "fdf1", "2216sdf", "fdf1", "2216sdf", "fdf1"};

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
                updateDataset();
                updateRecyclerAdapter();
            }
        });
    }

    private void updateDataset() {
        int len = dataset.length;
        String[] tmp = new String[len+1];
        for(int i = 1; i <= len; i++) {
            tmp[i] = dataset[i-1];
        }
        tmp[0] = toDo.getText().toString();
        dataset = tmp;

        toDo.setText("");
    }

    private void setRecyclerView() {
        memoRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        memoRecyclerView.setLayoutManager(layoutManager);



        mAdapter = new MyAdapter(dataset);


        memoRecyclerView.setAdapter(mAdapter);


    }

    private void updateRecyclerAdapter() {
        mAdapter = new MyAdapter(dataset);
        memoRecyclerView.setAdapter(mAdapter);
    }





}