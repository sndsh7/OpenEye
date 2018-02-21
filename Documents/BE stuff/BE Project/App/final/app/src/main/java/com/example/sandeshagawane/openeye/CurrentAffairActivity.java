package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentAffairActivity extends AppCompatActivity {

    //firebase auth
    private FirebaseAuth mAuth;

    private ProgressBar CurrentAffairProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affair);

        mAuth = FirebaseAuth.getInstance();

        CurrentAffairProgressbar= (ProgressBar) findViewById(R.id.current_affair_progressbar);



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

        Intent mainIntent = new Intent(CurrentAffairActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
