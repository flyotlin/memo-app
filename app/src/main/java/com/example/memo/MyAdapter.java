package com.example.memo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    public MyAdapter(ArrayList<String> myDataset) {
        this.mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout memoLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_memo, parent, false);
        ViewHolder holder = new ViewHolder(memoLayout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addMemo(String str) {
        mDataset.add(0, str);
        notifyDataSetChanged();
    }

    public void removeMemo(int position) {
        mDataset.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<String> getDataset() {
        return mDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.memo_view);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeMemo(getAdapterPosition());
                }
            });
        }
    }


}

