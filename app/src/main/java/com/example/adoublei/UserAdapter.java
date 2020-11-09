package com.example.adoublei;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.security.AccessControlContext;
import java.util.ArrayList;



public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private ArrayList<UserImageList> arrayList;
    private AccessControlContext context;

    public UserAdapter(ArrayList<UserImageList> arrayList, AccessControlContext context){
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.imageView2);
        holder.title.setText(arrayList.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return (arrayList !=null?arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView2;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView2 = itemView.findViewById(R.id.imageView2);
            this.title = itemView.findViewById(R.id.title);

        }
    }
}

