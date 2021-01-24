package com.example.adoublei.masking;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.adoublei.R;

public class MaskingActivity extends AppCompatActivity {

    private ImageView beforeMasking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView)findViewById(R.id.imageMasking);
        beforeMasking.setImageBitmap(image);

    }
}