package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mainToolBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mainToolBar = (android.support.v7.widget.Toolbar) findViewById (R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        getSupportActionBar().setTitle("Open Eye");


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {

            sendTologin();

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.action_logoutBtn:

                logOut();

            return true;

            default:
                return false;
        }

    }

    private void logOut() {
        mAuth.signOut();
        sendTologin();

    }

    private void sendTologin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }
}
