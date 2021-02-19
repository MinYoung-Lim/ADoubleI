package com.example.adoublei.masking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adoublei.DetailActivity;
import com.example.adoublei.FileUploadUtils;
import com.example.adoublei.ItemObject;
import com.example.adoublei.MainUpload;
import com.example.adoublei.R;
import com.example.adoublei.masking.MaskingItem;
import com.example.adoublei.masking.MaskingAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import static com.example.adoublei.masking.MaskingAutoActivity.NumOfClass_Masking;
import static com.example.adoublei.masking.MaskingAutoActivity.result;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    Button button_option;
//    static String[] result_ex1 = {"id","0.47440708","0.3533485","0.6719552","0.38725245"};
//    static String[] result_ex2 = {"name","0.59669155","0.29243743","0.686663","0.3303905"};
//    static String[] result_ex3 = {"address","0.4680234","0.365", "70442","0.70887196","0.4280191"};
//    static int NumOfClass_ex = 3;
//    static String[][] result = new String[8][5];

    static String[] label_name = new String[NumOfClass_Masking];

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

        // 은지야! 서버 연결안했을 때는 밑에 4줄 주석 해제하고 해야해!
//        result[0] = result_ex1;
//        result[1] = result_ex2;
//        result[2] = result_ex3;



        //내용입력
        for(int i = 0; i < NumOfClass_Masking; i++){
            MaskingItem op = new MaskingItem();
            String name = result[i][0];
            op.setTitle(name);
            mItem.add(op);
        }


        maskingAdapter = new MaskingAdapter(mItem);
        maskingOption.setAdapter(maskingAdapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(maskingOption.getContext(),lLayoutManager.getOrientation());
        maskingOption.addItemDecoration(dividerItemDecoration);

        maskingOption.addOnItemTouchListener(new RecyclerTouchListener(getContext(), maskingOption, new BottomSheetDialog.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                MaskingItem op = mItem.get(position);
                listener.onButtonClicked(view, op.getTitle());

                Log.e("title",op.getTitle());

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private BottomSheetDialog.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final BottomSheetDialog.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public interface BottomSheetListener {
        void onButtonClicked(View view, String text);

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
