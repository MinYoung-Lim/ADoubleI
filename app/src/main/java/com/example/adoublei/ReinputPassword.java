package com.example.adoublei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReinputPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private static final String TAG = "ReinputPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_reinput_password);

     //   findViewById(R.id.btn_to_fingerprint).setOnClickListener(onClickListener);

    }

    @Override
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
                    checkPassword();
                    Intent intent = getIntent();
                    String name = intent.getStringExtra("name");
                    Intent intent2 = new Intent(ReinputPassword.this, InputFingerprint.class);
                    intent2.putExtra("name", name);
                    startActivity(intent2);
                    break;


            }
        }
    };
/*
    private void signup(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if(email.length() > 0 && password.length() > 0 &&passwordCheck.length()>0){
            if(password.equals(passwordCheck)) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Sign in success, update UI with the signed-in user's information
                                    startToast("회원가입에 성공했습니다.");
                                    // updateUI(user);
                                } else {
                                    if(task.getException() != null){
                                        startToast(task.getException().toString());
                                    }
                                    // If sign in fails, display a message to the user.
                                    //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    // Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }

                                // ...
                            }
                        });
            }else{
                Toast.makeText(this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            }
        }
      }
*/


    private void checkPassword(){
        Intent intent = getIntent();
        String password = intent.getStringExtra("password");
        String email = intent.getStringExtra("email");

        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if (passwordCheck.equals(password)) {
        if(passwordCheck.length()>0) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Sign in success, update UI with the signed-in user's information
                                    startToast("회원가입에 성공했습니다.");
                                    // updateUI(user);
                                } else {
                                    if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                    // If sign in fails, display a message to the user.
                                    //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    // Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }


                                // ...
                            }
                        });
            } else {
               startToast("비밀번호가 일치하지 않습니다.");
            }
        }


    }
    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    private void startInputFingerprint(){
        Intent intent = new Intent(this,InputFingerprint.class);

        startActivity(intent);
    }

}