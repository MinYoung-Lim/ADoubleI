package com.example.adoublei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class InputNameRealActivity extends AppCompatActivity {

    ImageView iv_next, iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name_real);
        // Initialize Firebase Auth
        //  mAuth = FirebaseAuth.getInstance();

        iv_next = findViewById(R.id.iv_next);
        iv_back = findViewById(R.id.iv_back);

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputname();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    @Override
    public void onBackPressed()  // 뒤로가기 방지
    {
        //super.onBackPressed();
    }
    private void inputname(){

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = ((EditText)findViewById(R.id.et_name)).getText().toString();


        if(name.length()>0){
            Intent intent2 = new Intent(getApplicationContext(), InputPwdRealActivity.class);
            intent2.putExtra("email",email);
            intent2.putExtra("name", name);
            startActivity(intent2);


        }else{
            startToast("이름을 입력해주세요.");

        }

    }


    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

}