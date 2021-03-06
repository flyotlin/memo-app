package com.example.memo;


import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    public DoneAdapter(ArrayList<String> myDataset) {
        this.mDataset = myDataset;
    }

    @NonNull
    @Override
    public DoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout memoLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_done, parent, false);
        ViewHolder holder = new ViewHolder(memoLayout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoneAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void removeMemo(int position) {
        mDataset.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<String> getDataset() {
        return mDataset;
    }

    //    ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            setListeners();
        }

        private void initViews(View itemView) {
            textView = itemView.findViewById(R.id.done_view);
            deleteButton = itemView.findViewById(R.id.done_deleteButton);
        }

        private void setListeners() {
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    makeToast(v);
                    setVibrate(v);
                    setCopyToClipBoard(v);
                    return false;
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeMemo(getAdapterPosition());
                }
            });
        }

        private void makeToast(View v) {
            Toast.makeText(v.getContext(), "已複製: " + textView.getText().toString(), Toast.LENGTH_SHORT).show();
        }

        private void setVibrate(View v) {
            Vibrator myVibrator = (Vibrator) v.getContext().getSystemService(Service.VIBRATOR_SERVICE);
            myVibrator.vibrate(50);
        }

        private void setCopyToClipBoard(View v) {
            String text = textView.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, text);
            clipboard.setPrimaryClip(clipData);
        }
    }

}

