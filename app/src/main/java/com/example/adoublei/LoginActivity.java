package com.example.adoublei;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEtPassword;
    private static final String TAG = "SignUpActivity";

    private SharedPreferences loginInformation;
    private SharedPreferences.Editor editor;
    private FirebaseDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        setContentView(R.layout.activity_login);   //갤럭시노트3 공기계로 실행해보는데 여기서 오류뜸 -> 아예 실행안되고 중지됨! 에뮬레이터는 잘돼

        findViewById(R.id.btn_to_signup).setOnClickListener(onClickListener);
        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        mEtPassword = (EditText)findViewById(R.id.passwordCheckEditText);
        mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

/*        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e("회원탈퇴", "성공");
                        }
                        else
                            Log.e("회원탈퇴", "실패");
                    }
                });*/

        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null) {
            Intent intent = new Intent(getApplicationContext(), LoginPwdActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Intent intent1 = new Intent(getApplicationContext(),InputEmailRealActivity.class);
            startActivity(intent1);
            this.finish();
        }

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
                case R.id.loginButton:
                    /*Log.e("클릭","클릭");
                    signin();
                    Intent intent = getIntent();
                    String name = intent.getStringExtra("name");
                    Intent intent2 = new Intent(LoginActivity.this, LoginPwdActivity.class);
                    intent2.putExtra("name",name);
                    startActivity(intent2);*/
                    Intent intent = new Intent(getApplicationContext(), LoginPwdActivity.class);
                    startActivity(intent);
                       break;
                case R.id.btn_to_signup:
                    //Log.e("클릭","클릭");
                    Intent intent1 = new Intent(getApplicationContext(),InputEmailRealActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    };



    public void signin(){

        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if(email.length() > 0 && password.length() > 0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                startToast("로그인에 성공했습니다.");
                            } else {
                                if(task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }

                        }
                    });


        }else{
            startToast("비밀번호를 입력해 주세요.");
        }

    }
    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    public void startMainActivity(){
        Intent intent2 = new Intent(this, MainUpload.class);
        startActivity(intent2);


    }

}
