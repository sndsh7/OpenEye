package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private Button loginUsingAadhar;
    private Button loginUsingVoterID;
    private Button loginUsingMobile;
    private Button backToLogin;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        loginUsingAadhar = (Button) findViewById(R.id.reg_using_aadhar);
        loginUsingVoterID = (Button) findViewById(R.id.reg_using_voterID);
        loginUsingMobile = (Button) findViewById(R.id.reg_using_mobile);
        backToLogin = (Button) findViewById(R.id.backToLogin);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(backToLogin);
                finish();
            }
        });

        loginUsingAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent aadharRegIntent = new Intent(RegisterActivity.this, AadharRegActivity.class);
                startActivity(aadharRegIntent);
                finish();

            }
        });

        loginUsingVoterID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent voterIdRegIntent = new Intent(RegisterActivity.this, VoterIdRegActivity.class);
                startActivity(voterIdRegIntent);
                finish();

            }
        });

        loginUsingMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mobileRegIntent = new Intent(RegisterActivity.this, MobileRegActivity.class);
                startActivity(mobileRegIntent);
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            sendTomain();

        }

    }

    private void sendTomain() {

        Intent regIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(regIntent);
        finish();

    }
}
