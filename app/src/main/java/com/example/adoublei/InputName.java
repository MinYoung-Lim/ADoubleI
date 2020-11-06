package com.example.adoublei;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class InputName extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "InputName";

    public class User {

        public String username;
        public String email;

        public User(){

        }


        private DatabaseReference mDatabase;


        public User(String username, String email){

            this.username = username;
            this.email= email;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_input_name);
        // Initialize Firebase Auth

        findViewById(R.id.btn_to_fingerprint).setOnClickListener(onClickListener);
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // case R.id.signUpButton:

                //    signup();
                //   break;
                // case R.id.gotoLoginButton:
                //    myStartActivity(LoginActivity.class);
                /*case R.id.loginButton:
                    startLoginActivity();
                    break;
                    */

                case R.id.btn_to_fingerprint:
                    inputname();
                    //startInputPassword();
                    break;


            }
        }
    };
    private void inputname(){

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();


        if(name.length()>0){
            Intent intent2 = new Intent(InputName.this,InputPassword.class);

            intent2.putExtra("email",email);
            intent2.putExtra("name", name);


            Log.e("email",email);
            Log.e("name",name);

            startActivity(intent2);


        }else{
            startToast("이름을 입력해주세요.");

        }

    }



    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }



}