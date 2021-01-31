package com.example.adoublei.masking;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.adoublei.R;

import java.util.ArrayList;

public class MaskingAutoActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    private ImageView beforeMasking;
    private ArrayList<MaskingItem> mItem = new ArrayList<>();
    private Bitmap image;
    private Bitmap afterMasking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking_auto);

        byte[] arr = getIntent().getByteArrayExtra("image");
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView)findViewById(R.id.imageMasking);
        beforeMasking.setImageBitmap(image);
        afterMasking = Bitmap.createBitmap(image).copy(Bitmap.Config.ARGB_8888,true);




        Button buttonOpenBottomSheet = findViewById(R.id.buttonOpenBottomSheet);
        buttonOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "detailBottomSheet");
            }
        });


    }

    @Override
    public void onSwitchChecked(boolean checked) {
        if (checked == true) {
            Canvas canvas = new Canvas(afterMasking);

            canvas.drawRect(100, 100, 200, 200, new Paint());

            beforeMasking.setImageBitmap(afterMasking);
        } else{
            beforeMasking.setImageBitmap(image);
        }
    }
}