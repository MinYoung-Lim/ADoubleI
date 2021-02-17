package com.example.adoublei;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.adoublei.UserData.password;

public class LoginPwdActivity extends AppCompatActivity {

    // 2020.12.26 임민영
    // 비밀번호로 로그인하는 액티비티
    // 기본적인 기능들은 다 구현함
    // checkPwdIsFull()에서 Firebase에서 가져온 비밀번호와 pwd가 같은지 비교하는 코드만 추가하면 됨

    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_0;
    ImageView iv_circle_1, iv_circle_2, iv_circle_3, iv_circle_4, iv_circle_5, iv_circle_6;
    ImageView iv_backspace, iv_back;
    private StringBuffer pwd = new StringBuffer("");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pwd_real);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);
        tv_5 = findViewById(R.id.tv_5);
        tv_6 = findViewById(R.id.tv_6);
        tv_7 = findViewById(R.id.tv_7);
        tv_8 = findViewById(R.id.tv_8);
        tv_9 = findViewById(R.id.tv_9);
        tv_0 = findViewById(R.id.tv_0);

        iv_circle_1 = findViewById(R.id.iv_circle_1);
        iv_circle_2 = findViewById(R.id.iv_circle_2);
        iv_circle_3 = findViewById(R.id.iv_circle_3);
        iv_circle_4 = findViewById(R.id.iv_circle_4);
        iv_circle_5 = findViewById(R.id.iv_circle_5);
        iv_circle_6 = findViewById(R.id.iv_circle_6);

        iv_backspace = findViewById(R.id.iv_backspace);
        iv_back = findViewById(R.id.iv_back);

        SetOnClickListener onClickListener = new SetOnClickListener();
        tv_1.setOnClickListener(onClickListener);
        tv_2.setOnClickListener(onClickListener);
        tv_3.setOnClickListener(onClickListener);
        tv_4.setOnClickListener(onClickListener);
        tv_5.setOnClickListener(onClickListener);
        tv_6.setOnClickListener(onClickListener);
        tv_7.setOnClickListener(onClickListener);
        tv_8.setOnClickListener(onClickListener);
        tv_9.setOnClickListener(onClickListener);
        tv_0.setOnClickListener(onClickListener);

        iv_backspace.setOnClickListener(onClickListener);






    }

    @Override
    public void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }



    @Override
    public void onBackPressed()  // 뒤로가기 방지
    {
        //super.onBackPressed();
    }

    class SetOnClickListener implements TextView.OnClickListener{

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.tv_1:
                    pwd.append("1");
                    break;
                case R.id.tv_2:
                    pwd.append("2");
                    break;
                case R.id.tv_3:
                    pwd.append("3");
                    break;
                case R.id.tv_4:
                    pwd.append("4");
                    break;
                case R.id.tv_5:
                    pwd.append("5");
                    break;
                case R.id.tv_6:
                    pwd.append("6");
                    break;
                case R.id.tv_7:
                    pwd.append("7");
                    break;
                case R.id.tv_8:
                    pwd.append("8");
                    break;
                case R.id.tv_9:
                    pwd.append("9");
                    break;
                case R.id.tv_0:
                    pwd.append("0");
                    break;
                case R.id.iv_backspace:
                    deletePwd();
                    break;

            }

            changeCircle();  // 비밀번호 입력 시 원 채우기
            checkPwdIsFull();  // 비밀번호 모두 입력했는지 확인 후 다음 액티비티 실행
            Log.e("pwd", String.valueOf(pwd));

        }
    }

    private void deletePwd() {

        if(pwd.length()==5)
            iv_circle_5.setImageResource(R.drawable.password_yet);
        else if(pwd.length()==4)
            iv_circle_4.setImageResource(R.drawable.password_yet);
        else if(pwd.length()==3)
            iv_circle_3.setImageResource(R.drawable.password_yet);
        else if(pwd.length()==2)
            iv_circle_2.setImageResource(R.drawable.password_yet);
        else if(pwd.length()==1)
            iv_circle_1.setImageResource(R.drawable.password_yet);
        else if(pwd.length()==6)
            iv_circle_6.setImageResource(R.drawable.password_yet);

        pwd.deleteCharAt(pwd.length()-1);

    }

    private void checkPwdIsFull() {

        //sharedpreferences에 저장된 email 가져오기
        //SharedPreferences prefs = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        //String email = prefs.getString("email", "NULL"); //키값, 디폴트값

        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        //Log.e("email", email);



        if (pwd.length()==6) {
            mAuth.signInWithEmailAndPassword(email, String.valueOf(pwd))
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.e("로그인", "성공");

                                Intent intent = new Intent(getApplicationContext(), MainUpload.class);
                                startActivity(intent);
                                finish();

                                //FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                Log.e("로그인", "실패");
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }



    }


    private void changeCircle() {

        switch (pwd.length()){
            case 1:
                iv_circle_1.setImageResource(R.drawable.password_okay);
                break;
            case 2:
                iv_circle_1.setImageResource(R.drawable.password_okay);
                iv_circle_2.setImageResource(R.drawable.password_okay);
                break;
            case 3:
                iv_circle_1.setImageResource(R.drawable.password_okay);
                iv_circle_2.setImageResource(R.drawable.password_okay);
                iv_circle_3.setImageResource(R.drawable.password_okay);
                break;
            case 4:
                iv_circle_1.setImageResource(R.drawable.password_okay);
                iv_circle_2.setImageResource(R.drawable.password_okay);
                iv_circle_3.setImageResource(R.drawable.password_okay);
                iv_circle_4.setImageResource(R.drawable.password_okay);
                break;
            case 5:
                iv_circle_1.setImageResource(R.drawable.password_okay);
                iv_circle_2.setImageResource(R.drawable.password_okay);
                iv_circle_3.setImageResource(R.drawable.password_okay);
                iv_circle_4.setImageResource(R.drawable.password_okay);
                iv_circle_5.setImageResource(R.drawable.password_okay);
                break;
            case 6:
                iv_circle_1.setImageResource(R.drawable.password_okay);
                iv_circle_2.setImageResource(R.drawable.password_okay);
                iv_circle_3.setImageResource(R.drawable.password_okay);
                iv_circle_4.setImageResource(R.drawable.password_okay);
                iv_circle_5.setImageResource(R.drawable.password_okay);
                iv_circle_6.setImageResource(R.drawable.password_okay);
                break;
        }

    }

}