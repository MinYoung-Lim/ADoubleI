package com.example.adoublei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class InputEmailRealActivity extends AppCompatActivity {

    ImageView iv_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_email2);

        iv_next = findViewById(R.id.iv_next);

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), InputNameRealActivity.class);
                startActivity(intent);

            }
        });



    }
}