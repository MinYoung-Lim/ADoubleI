package com.example.adoublei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MaskingAutoActivity extends AppCompatActivity {

    private ImageView beforeMasking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masking_auto);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        beforeMasking = (ImageView)findViewById(R.id.imageMasking);
        beforeMasking.setImageBitmap(image);

    }
}