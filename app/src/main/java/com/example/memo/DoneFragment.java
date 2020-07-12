package com.example.memo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoneFragment extends Fragment {
    private int done_num;

    private TextView doneNumber;
    private RecyclerView memoRecyclerView;
    private DoneAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> dataset = new ArrayList<>();
    private SharedPreferences database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        initViews(view);
        initDataset();
        setTextView();
        setRecyclerView();
        return view;
    }

    private void initViews(View view) {
        doneNumber = view.findViewById(R.id.done_number);
        memoRecyclerView = view.findViewById(R.id.memos_done);
    }

    private void initDataset() {
        database = getActivity().getSharedPreferences("done", 0);
        done_num = database.getInt("size", 0);

        if (done_num > 0) {
            for (int i = 0; i < done_num; i++) {
                String tmp = database.getString("done" + i, "");
                dataset.add(tmp);
            }
        }
    }

    private void setTextView() {
        doneNumber.setText(getResources().getString(R.string.nav_done) + ": " + done_num);
    }

    private void setRecyclerView() {
        memoRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        memoRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new DoneAdapter(dataset);
        memoRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveToDatabase();
    }

    private void saveToDatabase() {
        int size = dataset.size();
        database = getActivity().getSharedPreferences("done", 0);
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
}
