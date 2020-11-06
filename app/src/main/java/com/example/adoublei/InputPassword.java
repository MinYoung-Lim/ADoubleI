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
import com.google.firebase.database.FirebaseDatabase;

public class InputPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "InputPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_input_password);

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
                    inputpassword();
                   // startReInputPassword();
                    break;


            }
        }
    };

    public void inputpassword() {

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");

        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        if (password.length() > 0) {

            Intent intent2 = new Intent(InputPassword.this, ReinputPassword.class);
            intent2.putExtra("email", email);
            intent2.putExtra("name", name);
            intent2.putExtra("password", password);

            writeUser(name,password);

            startActivity(intent2);

            Log.e("email", email);
            Log.e("name", name);
            Log.e("password", password);

        } else {
            startToast("비밀번호를 입력하세요.");
        }
    }


    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    private void startReInputPassword(){
        Intent intent = new Intent(this,ReinputPassword.class);
        startActivity(intent);
    }
    private void writeUser(String name, String password){
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        UserData userdata = new UserData(name, password);
        mDatabase.child("users").child(name).push().setValue(userdata);
    }

}