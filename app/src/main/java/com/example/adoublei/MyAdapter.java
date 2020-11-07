package com.example.adoublei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;



class MyAdapter extends RecyclerView.Adapter {
   // private Context context;
    private ArrayList mItems;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    public MyAdapter(ArrayList items, Context mContext) {
        mItems = items;
       // context = mContext;
         } // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성

 @Override
 public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 새로운 뷰를 만든다
     View v = LayoutInflater.from(parent.getContext())
             .inflate(R.layout.item_cardview,parent,false);
     
     ViewHolder holder = new ViewHolder(v);
     return holder; 
    
    } // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(mItems.get(position).image);
        holder.textView.setText(mItems.get(position).imagetitle);
        setAnimation(holder.imageView, position); }
        // // 필수로 Generate 되어야 하는 메소드 3

    @Override
    public int getItemCount() { return mItems.size(); }
 }

