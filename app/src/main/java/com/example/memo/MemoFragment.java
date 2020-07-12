package com.example.memo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoFragment extends Fragment {
    private EditText toDo;
    private Button addMemo;

    private RecyclerView memoRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> dataset = new ArrayList<>();
    private SharedPreferences database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        initViews(view);
        initArrayListDataset();
        setOnAddMemoClicked();
        setRecyclerView();
        return view;
    }

    private void initViews(View view) {
        toDo = view.findViewById(R.id.edittext_todo);
        addMemo = view.findViewById(R.id.button_addMemo);
        memoRecyclerView = view.findViewById(R.id.memoes);
    }

    private void initArrayListDataset() {
        database = getActivity().getPreferences(0);
        int size = database.getInt("size", 0);

        if (size > 0) {
            for (int i = 0; i < size; i++) {
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

        layoutManager = new LinearLayoutManager(getContext());
        memoRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(dataset);
        memoRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveToDatabase();
    }

    private void saveToDatabase() {
        int size = dataset.size();
        database = getActivity().getPreferences(0);
        ArrayList<String> dataset = mAdapter.getDataset();

        for (int i = 0; i < size; i++) {
            database.edit()
                    .putInt("size", size)
                    .putString("memo" + i, dataset.get(i))
                    .commit();
        }
    }
}
