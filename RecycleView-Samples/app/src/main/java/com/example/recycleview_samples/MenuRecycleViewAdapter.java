package com.example.recycleview_samples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by pxl on 2019/9/11 0011 下午 4:22.
 * Describe:
 */
public class MenuRecycleViewAdapter extends RecyclerView.Adapter<MainActivity.Holder> {
    private List<String> mData;

    public MenuRecycleViewAdapter(List<String> mData) {
        this.mData = mData;
    }

    public OnItemClickListener itemClickListener;
    public  void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    @NonNull
    @Override
    public MainActivity.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new MainActivity.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivity.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
