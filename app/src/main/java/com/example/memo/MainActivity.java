package com.example.memo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// @auther: flyotlin on 2020.07.01

// ItemTouchHelper
public class MainActivity extends AppCompatActivity {

    private EditText toDo;
    private Button addMemo;

    private RecyclerView memoRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> dataset = new ArrayList<>();
    private SharedPreferences database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initArrayListDataset();
        setOnAddMemoClicked();
        setRecyclerView();
    }

    private void initViews() {
        toDo = (EditText) findViewById(R.id.edittext_todo);
        addMemo = (Button) findViewById(R.id.button_addMemo);
        memoRecyclerView = (RecyclerView) findViewById(R.id.memoes);
    }

    private void initArrayListDataset() {
        database = getPreferences(0);
        int size = database.getInt("size", 0);

        if(size > 0) {
            for(int i = 0; i < size; i++) {
                String tmp = database.getString("memo" + i, "");
                dataset.add(tmp);
            }
        }
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

    @Override
    protected void onStop() {
        super.onStop();

        saveToDatabase();
    }

    private void saveToDatabase() {
        int size = dataset.size();
        database = getPreferences(0);
        ArrayList<String> dataset = mAdapter.getDataset();

        for(int i = 0; i < size; i++) {
            database.edit()
                    .putInt("size", size)
                    .putString("memo" + i, dataset.get(i))
                    .commit();
        }
    }


}