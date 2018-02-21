package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AadharRegActivity extends AppCompatActivity {
    public static TextView tv_sd_uid,tv_sd_name,tv_sd_gender,tv_sd_yob,tv_sd_vtc,tv_sd_password,tv_sd_conpass,tv_sd_mob;
    public static String uid,state,country;
    private FirebaseAuth mAuth;
    private Button BacktoReg,QRScanBtn;
    private ProgressBar AadharProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_reg);

        mAuth = FirebaseAuth.getInstance();

        tv_sd_name = (TextView)findViewById(R.id.editName);
        tv_sd_uid= (TextView)findViewById(R.id.editUid);
        tv_sd_gender = (TextView)findViewById(R.id.editGender);
        tv_sd_yob = (TextView)findViewById(R.id.editDOB);
        tv_sd_vtc = (TextView)findViewById(R.id.editAddress);
        tv_sd_password= (TextView)findViewById(R.id.editPass);
        tv_sd_conpass= (TextView)findViewById(R.id.editConfPass);
        tv_sd_mob= (TextView)findViewById(R.id.editMobile);
        BacktoReg=(Button)findViewById(R.id.BackToMReg);
        QRScanBtn=(Button)findViewById(R.id.QRScanBtn);


        //On click Listner
        QRScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent QRScanIntent = new Intent(AadharRegActivity.this, QRScanActivity.class);
                startActivity(QRScanIntent);
                finish();
            }
        });

        BacktoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BacktoRegIntent = new Intent(AadharRegActivity.this, RegisterActivity.class);
                startActivity(BacktoRegIntent);
                finish();
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

        Intent mainIntent= new Intent(AadharRegActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

}
