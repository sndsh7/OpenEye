package com.example.sandeshagawane.openeye;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar setupToolBar;
    private CircleImageView default_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //toolbar
        setupToolBar= (android.support.v7.widget.Toolbar) findViewById(R.id.setupToolbar);
        setSupportActionBar(setupToolBar);
        getSupportActionBar().setTitle("Setup Profile");


    }

    /**
     * Change profile Image
     * @param view
     */
    public void ChangeProfileImage(View view){

    }
}
