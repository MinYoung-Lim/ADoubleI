package com.example.adoublei;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainUpload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 다량의 데이터
        // 2. Adapter
        // 3. AdapterView - GridView
        int img[] = {
                R.drawable.c, R.drawable.e, R.drawable.j
        };

        // 커스텀 아답타 생성
        MyAdapter adapter = new MyAdapter (
                getApplicationContext(),
                R.layout.row,       // GridView 항목의 레이아웃 row.xml
                img);    // 데이터

        GridView gv = (GridView)findViewById(R.id.gridView1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용


    } // end of onCreate
} // end of class


