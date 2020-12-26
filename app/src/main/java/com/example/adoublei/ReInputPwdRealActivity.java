package com.example.adoublei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReInputPwdRealActivity extends AppCompatActivity {

    // 2020.12.26 임민영
    // InputPwdRealActivity와 대부분 코드가 같고 추가적으로 해당 액티비티에서 받아온 비밀번호와 비교하는 기능 구현함
    // checkPwdIsFull() 안에 Firebase에 비밀번호 등록하는 코드 입력해야함

    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_0;
    ImageView iv_circle_1, iv_circle_2, iv_circle_3, iv_circle_4, iv_circle_5, iv_circle_6;
    ImageView iv_backspace, iv_back;
    private StringBuffer pwd2 = new StringBuffer("");  // 이 액티비티에서 입력받은 비밀번호
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_input_pwd_real);

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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        pwd = intent.getStringExtra("pwd");  // 이전 액티비티에서 받아온 pwd저장

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
                    pwd2.append("1");
                    break;
                case R.id.tv_2:
                    pwd2.append("2");
                    break;
                case R.id.tv_3:
                    pwd2.append("3");
                    break;
                case R.id.tv_4:
                    pwd2.append("4");
                    break;
                case R.id.tv_5:
                    pwd2.append("5");
                    break;
                case R.id.tv_6:
                    pwd2.append("6");
                    break;
                case R.id.tv_7:
                    pwd2.append("7");
                    break;
                case R.id.tv_8:
                    pwd2.append("8");
                    break;
                case R.id.tv_9:
                    pwd2.append("9");
                    break;
                case R.id.tv_0:
                    pwd2.append("0");
                    break;
                case R.id.iv_backspace:
                    deletePwd();
                    break;

            }

            changeCircle();  // 비밀번호 입력 시 원 채우기
            checkPwdIsFull();  // 비밀번호 모두 입력했는지 확인 후 다음 액티비티 실행
            Log.e("pwd", String.valueOf(pwd2));

        }
    }

    private void deletePwd() {

        if(pwd2.length()==5)
            iv_circle_5.setImageResource(R.drawable.password_yet);
        else if(pwd2.length()==4)
            iv_circle_4.setImageResource(R.drawable.password_yet);
        else if(pwd2.length()==3)
            iv_circle_3.setImageResource(R.drawable.password_yet);
        else if(pwd2.length()==2)
            iv_circle_2.setImageResource(R.drawable.password_yet);
        else if(pwd2.length()==1)
            iv_circle_1.setImageResource(R.drawable.password_yet);
        else if(pwd2.length()==6)
            iv_circle_6.setImageResource(R.drawable.password_yet);

        pwd2.deleteCharAt(pwd2.length()-1);

    }

    private void checkPwdIsFull() {
        if (pwd2.length()==6){

            if(pwd.equals(pwd2.toString())){  // 비밀번호가 같으면 Firebase에 업로드
                // 업로드하는 코드 작성해야함!



                // 업로드 후 메인 화면으로 이동
                Intent intent2 = new Intent(getApplicationContext(), MainUpload.class);
                startActivity(intent2);
            }
            else{  // 비밀번호 다르면

                Toast.makeText(this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show();

            }

        }
    }


    private void changeCircle() {

        switch (pwd2.length()){
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