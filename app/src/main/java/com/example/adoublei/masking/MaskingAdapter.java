package com.example.adoublei.masking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adoublei.R;

import java.util.ArrayList;

public class MaskingAdapter extends RecyclerView.Adapter<MaskingAdapter.RecyclerViewHolders> {

    private Context context;
    private ArrayList<MaskingItem> mItem;

    public MaskingAdapter(ArrayList<MaskingItem> item){
        this.context = context;
        this.mItem = item;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        protected TextView title;
        protected Switch option;

        public RecyclerViewHolders(View MaksingItem) {
            super(MaksingItem);
            this.title = (TextView) itemView.findViewById(R.id.title_option);
            this.option = (Switch) itemView.findViewById(R.id.switch_option);

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
        holder.option.setChecked(mItem.get(position).getOption());
//        holder.option.setOnCheckedChangeListener(
//            new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//
//                    }
//                    else{
//                    }
//                }
//            }
//        );
    }

    @Override
    public int getItemCount() {
        return (null != mItem ? mItem.size() : 0);
    }
}




