package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VoterIdRegActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button backTOreg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_id_reg);

        mAuth = FirebaseAuth.getInstance();

        backTOreg = (Button) findViewById(R.id.backToReg);

        backTOreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent backtoReg = new Intent(VoterIdRegActivity.this, RegisterActivity.class);
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

        Intent mainIntent = new Intent(VoterIdRegActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

}
