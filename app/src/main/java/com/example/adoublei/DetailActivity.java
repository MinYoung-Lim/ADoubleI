package com.example.adoublei;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);  // layout xml 과 자바파일을 연결

        Intent intent = new Intent(getBaseContext(),DetailActivity.class);
    } // end onCreate()



}
