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

public class MaskingAutoActivity extends AppCompatActivity {

    private ImageView beforeMasking;
    private ArrayList<MaskingItem> mItem = new ArrayList<>();
    MaskingAdapter maskingAdapter =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking_auto);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView)findViewById(R.id.imageMasking);
        //beforeMasking.setImageBitmap(image);

        Bitmap newImage = Bitmap.createBitmap(image).copy(Bitmap.Config.ARGB_8888,true);
        Canvas canvas = new Canvas(newImage);

        canvas.drawLine(0,0,200,0,new Paint());
        canvas.drawLine(200,0,200,200,new Paint());
        canvas.drawLine(200,200,0,200,new Paint());
        canvas.drawLine(0,200,0,0,new Paint());
        canvas.drawCircle(100,100,50,new Paint());

        beforeMasking.setImageBitmap(newImage);


        Button buttonOpenBottomSheet = findViewById(R.id.buttonOpenBottomSheet);
        buttonOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "detailBottomSheet");
            }
        });


    }
}