package com.example.adoublei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                inputEmail();

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        //updateUI(currentUser);

    }
    private void inputEmail(){
        String email = ((EditText)findViewById(R.id.et_email)).getText().toString();

        // String idByAndroid_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);


        if(email.length()>0){

            Intent intent = new Intent(getApplicationContext(), InputNameRealActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);



        }else{
            startToast("이메일을 입력해주세요.");
        }
    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}