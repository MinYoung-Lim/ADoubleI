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
//        maskingOption = view.findViewById(R.id.maskingOption);
//        int numberOfColumns = 1;
//        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(),numberOfColumns);
//        maskingOption.setLayoutManager(mGridLayoutManager);
//
//        MaskingItem op = new MaskingItem();
//        op.setTitle("이름");
//        op.setOption(true);
//
//        maskingAdapter = new MaskingAdapter(mItem);
//        maskingOption.setAdapter(maskingAdapter);
//
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(maskingOption.getContext(),mGridLayoutManager.getOrientation());
//        maskingOption.addItemDecoration(dividerItemDecoration);

    }

    public interface BottomSheetListener {
        void onSwitchChecked();

        void onSwitchChecked(boolean checked);
    }

}

