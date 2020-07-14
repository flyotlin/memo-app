package com.example.memo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
        database = getActivity().getSharedPreferences("memos", 0);
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    mAdapter.removeMemo(position, true);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    mAdapter.removeMemo(position, false);
                }
            }
        }).attachToRecyclerView(memoRecyclerView);

    }

    @Override
    public void onPause() {
        super.onPause();
        saveToMemoDatabase();
        saveToDoneDatabase();
    }

    private void saveToMemoDatabase() {
        int size = dataset.size();
        database = getActivity().getSharedPreferences("memos", 0);
        ArrayList<String> dataset = mAdapter.getDataset();

        if (size == 0) {
            database.edit().clear().commit();
        } else {
            for (int i = 0; i < size; i++) {
                database.edit()
                        .putInt("size", size)
                        .putString("memo" + i, dataset.get(i))
                        .commit();
            }
        }
    }

    private void saveToDoneDatabase() {
        ArrayList<String> dataset = mAdapter.getDoneDataset();
        database = getActivity().getSharedPreferences("done", 0);

        int database_size = database.getInt("size", 0);
        int dataset_size = dataset.size();
        int size = database_size + dataset_size;
        for (int i = 0; i < dataset_size; i++) {
            database.edit()
                    .putInt("size", size)
                    .putString("done" + (database_size + i), dataset.get(i))
                    .commit();
        }
    }
}
