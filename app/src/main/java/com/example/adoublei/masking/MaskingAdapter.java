package com.example.adoublei.masking;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adoublei.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MaskingAdapter extends RecyclerView.Adapter<MaskingAdapter.RecyclerViewHolders> {

    private Context context;
    private ArrayList<MaskingItem> mItem;
    private OnItemClickListener listener;
    private int bool = -1;

    public MaskingAdapter(ArrayList<MaskingItem> item){
        //this.context = context;
        this.mItem = item;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected Button title;

        public RecyclerViewHolders(@NonNull View itemView) {
            super(itemView);

            this.title = (Button) itemView.findViewById(R.id.button_option);

        }

        @Override
        public void onClick(View v) {
            if(listener!=null)
            {
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                {
                    listener.onItemClick(v, position);
                    if (mItem.get(position).getChecked() == true){
                        v.setSelected(true);
                    }else{
                        v.setSelected(false);
                    }

                }


            }

        }
    }


    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.maskinglist,viewGroup,false);
        RecyclerViewHolders viewHolders=new RecyclerViewHolders(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {

        //holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        //holder.title.setGravity(Gravity.LEFT);
        holder.title.setText(mItem.get(position).getTitle());
        if (mItem.get(position).getChecked() == true){
            holder.title.setSelected(true);
        }

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mItem ? mItem.size() : 0);
    }
}



