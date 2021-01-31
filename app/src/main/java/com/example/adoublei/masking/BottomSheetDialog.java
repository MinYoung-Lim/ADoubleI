package com.example.adoublei.masking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adoublei.R;
import com.example.adoublei.masking.MaskingItem;
import com.example.adoublei.masking.MaskingAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private ArrayList<MaskingItem> mItem = new ArrayList<>();
    MaskingAdapter maskingAdapter =null;
    private BottomSheetListener listener;
    private RecyclerView maskingOption = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheetlayout, container, false);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maskingOption = view.findViewById(R.id.maskingOption);
        LinearLayoutManager lLayoutManager = new LinearLayoutManager((getContext()));
        maskingOption.setLayoutManager(lLayoutManager);

        //내용입력
       for (int i=0; i<10; i++){
            MaskingItem op = new MaskingItem();
            op.setTitle("이름");
            mItem.add(op);
        }

        maskingAdapter = new MaskingAdapter(mItem);
        maskingOption.setAdapter(maskingAdapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(maskingOption.getContext(),lLayoutManager.getOrientation());
        maskingOption.addItemDecoration(dividerItemDecoration);

        for (int i=0; i<10; i++){
            if (mItem.get(i).getOption() == true){
                listener.onSwitchChecked(true);
            }
            else{
                listener.onSwitchChecked(true);
            }
        }


    }

    public interface BottomSheetListener {
        void onSwitchChecked(boolean checked);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (BottomSheetListener) context;

        }catch (ClassCastException e){
            Log.e("error","onAttach error");
            //throw new ClassCastException(context.toString()+" need to impliment BottomSheetListener interface.");
        }
    }

}

