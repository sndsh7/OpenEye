package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginBtn;
    private Button newRegBtn;

    private ProgressBar loginProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginUsername = (EditText) findViewById(R.id.username);
        loginPassword = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        newRegBtn = (Button) findViewById(R.id.new_reg);
        loginProgress = (ProgressBar) findViewById(R.id.login_Progress);

        newRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newRegIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(newRegIntent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginUser = loginUsername.getText().toString();
                String loginPass = loginPassword.getText().toString();

                if(!TextUtils.isEmpty(loginUser) && !TextUtils.isEmpty(loginPass)) {
                    loginProgress.setVisibility(View.VISIBLE);


                    mAuth.signInWithEmailAndPassword(loginUser, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                sendToMain();


                            } else {

                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error :" + errorMessage, Toast.LENGTH_LONG).show();

                            }

                            loginProgress.setVisibility(View.INVISIBLE);

                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Please fill username and password",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= mAuth.getCurrentUser();

        if(currentUser != null){

            sendToMain();

        }

    }

    private void sendToMain(){

        Intent mainIntent= new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
