package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class MyNetaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private Spinner ward,council;
    private String ward_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_neta);

        inti();
        firebase();

    }

    private void firebase() {

        firebaseAuth = FirebaseAuth.getInstance();

    }

    /**
     * Initialize
     */
    private void inti() {
        //toolbar
        android.support.v7.widget.Toolbar setupToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.mynetaToolBar);
        setSupportActionBar(setupToolBar);
        getSupportActionBar().setTitle("My Neta");

        //UI elements
        ward = findViewById(R.id.ward_spinner);
        council = findViewById(R.id.council_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ward_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ward.setAdapter(adapter);
        ward.setOnItemSelectedListener(this);

    }

    /**
     * Toolbar
     */

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

                case R.id.action_home_btn:

                    home();
                    return true;

                case R.id.action_setup_btn:

                    setup_activity();
                    return true;

                default:
                    return false;
            }

        }

    /**
     * Main Activity Home
     */
    private void home() {
        Intent logoutIntent = new Intent(MyNetaActivity.this,MainActivity.class);
        startActivity(logoutIntent);
        finish();
    }

    /**
     * LOGOUT
     */
    private void logOut() {
        firebaseAuth.signOut();
        sendTologin();

    }

    private void sendTologin() {

        Intent logoutIntent = new Intent(MyNetaActivity.this,LoginActivity.class);
        startActivity(logoutIntent);
        finish();

    }

    private void setup_activity() {
        Intent setupIntent = new Intent(MyNetaActivity.this,SetupActivity.class);
        startActivity(setupIntent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ward_value = (String)ward.getSelectedItem();

        switch (ward_value){

            case "Ward R Central":
                wardrc();
                break;

            case "Ward R South":
                wardrs();

        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void wardrc() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ward_RC_council,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        council.setAdapter(adapter);

    }

    private void wardrs(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ward_RS_council,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        council.setAdapter(adapter);


    }

}
