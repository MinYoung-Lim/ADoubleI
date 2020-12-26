package com.example.adoublei;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputEmail extends AppCompatActivity {
    public static Context mContext;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "SignUpActivity";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_input_email2);


        //   findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.btn_to_name).setOnClickListener(onClickListener);



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

                case R.id.btn_to_name:

                    //startInputName();
                    inputEmail();
                    break;


            }
        }
    };

    private void inputEmail(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();

        // String idByAndroid_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);


        if(email.length()>0){

            Intent intent = new Intent(InputEmail.this,InputName.class);
            intent.putExtra("email", email);
            startActivity(intent);


            SharedPreferences loginInformaiton = getSharedPreferences(email,0);
            SharedPreferences.Editor editor = loginInformaiton.edit();
            editor.putString("email",email);
            editor.commit();



        }else{
            startToast("이메일을 입력해주세요.");
        }
    }

    /*  private void signup(){
  c       String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
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
          }else{
              startToast("이메일 또는 비밀번호를 입력해 주세요.");
          }

      }*/
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void startInputName(){
        Intent intent = new Intent(this,InputName.class);
        startActivity(intent);
    }

}