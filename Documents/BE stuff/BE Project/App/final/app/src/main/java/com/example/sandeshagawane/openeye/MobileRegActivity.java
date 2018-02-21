package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MobileRegActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button backTOreg;
    private Button checkbtn;
    private ProgressBar mobileProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_reg);

        mAuth = FirebaseAuth.getInstance();

        mobileProgressbar=(ProgressBar)findViewById(R.id.mobile_Progress);

        checkbtn= (Button)findViewById(R.id.cheack_btn);

        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileProgressbar.setVisibility(View.VISIBLE);

            }
        });


        backTOreg = (Button) findViewById(R.id.backToReg);


        backTOreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent backtoReg = new Intent(MobileRegActivity.this, RegisterActivity.class);
                startActivity(backtoReg);
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

        Intent mainIntent = new Intent(MobileRegActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
