package com.example.adoublei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InputFingerprint extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "InputFingerprint";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_input_fingerprint);

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);

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

                case R.id.loginButton:
                    startLoginActivity();
                    break;

            }
        }
    };

    public void startLoginActivity(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        Intent intent2 = new Intent(InputFingerprint.this, LoginActivity.class);
        intent2.putExtra("name", name);
        startActivity(intent2);
    }
}