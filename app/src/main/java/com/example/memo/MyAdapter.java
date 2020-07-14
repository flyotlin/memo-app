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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private ArrayList<String> doneDataset = new ArrayList<String>();

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

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int date = Calendar.getInstance().get(Calendar.DATE);
        String Date = year + "/" + month + "/" + date;
        holder.memoDate.setText(Date);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addMemo(String str) {
        mDataset.add(0, str);
        notifyDataSetChanged();
    }

    public void removeMemo(int position, boolean saveToDone) {
        if (saveToDone) {
            doneDataset.add(mDataset.get(position));
        }

        mDataset.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<String> getDataset() {
        return mDataset;
    }

    public ArrayList<String> getDoneDataset() {
        return doneDataset;
    }

    //    ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView memoDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();
            setListeners();
        }

        private void initViews() {
            textView = (TextView) itemView.findViewById(R.id.memo_view);
            memoDate = (TextView) itemView.findViewById(R.id.memo_date);
        }

        private void setListeners() {
            setLongClickListener(textView);
            setLongClickListener(memoDate);
        }

        private void setLongClickListener(TextView textView) {
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

