package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MyNetaActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar MynetaToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_neta);

        inti();

    }

    /**
     * Initialize
     */
    private void inti() {
        MynetaToolBar = (Toolbar)findViewById(R.id.mynetaToolBar);
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
        Intent logoutIntent = new Intent(MyNetaActivity.this,LoginActivity.class);
        startActivity(logoutIntent);
        finish();

    }
}
