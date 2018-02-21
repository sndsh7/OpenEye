package com.example.sandeshagawane.openeye;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zXingScannerView;
    private FirebaseAuth mAuth;
    ProgressDialog pd;

    public static String uid,name,gender,dateOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_reg);

        mAuth = FirebaseAuth.getInstance();

        zXingScannerView = new ZXingScannerView(this);
        zXingScannerView.startCamera();
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);

        pd = ProgressDialog.show(QRScanActivity.this,"","test",true);
        new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                pd.dismiss();
            };
        }.start();
        finish();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void handleResult(Result result) {
    processScanedData(result.getText());

    }

    private void processScanedData(String scanData) {
        XmlPullParserFactory pullParserFactory;
        try{
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));
            //parser in XML
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_DOCUMENT){
                }else if(eventType == XmlPullParser.START_TAG &&  "PrintLetterBarcodeData".equals(parser.getName())) {
                //Extract data from TAG
                    //UID
                    uid = parser.getAttributeValue(null,"uid");
                    //name
                    name=parser.getAttributeValue(null,"name");
                    //gender
                    gender=parser.getAttributeValue(null,"gender");
                    //Year
                    dateOfBirth= parser.getAttributeValue(null,"dob");
                    // village Tehsil
                    villageTehsil = parser.getAttributeValue(null,"vtc");
                    // Post Office
                    postOffice = parser.getAttributeValue(null,"po");
                    // district
                    district = parser.getAttributeValue(null,"dist");
                    // state
                    state = parser.getAttributeValue(null,"state");
                    // Post Code
                    postCode = parser.getAttributeValue(null,"pc");

                }else if(eventType == XmlPullParser.END_TAG) {
                    Log.d("Rishu","End tag "+parser.getName());

                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d("Rishu","Text "+parser.getText());

                }
                // update eventType
                eventType = parser.next();
            }

        }catch (XmlPullParserException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        displayScanedData(scanData);

    }

    private void displayScanedData(String res) {
        zXingScannerView.stopCameraPreview();
        zXingScannerView.stopCamera();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage("Scanned Successfully");
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        AadharRegActivity.tv_sd_name.setText(name);
        AadharRegActivity.tv_sd_uid.setText(uid);
        AadharRegActivity.tv_sd_gender.setText(gender);
        AadharRegActivity.tv_sd_vtc.setText(villageTehsil+", "+postOffice+", \n"+district+", "+state+", \n"+postCode);
        AadharRegActivity.tv_sd_yob.setText(dateOfBirth);
        AadharRegActivity.state=state;
        finish();
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

        Intent mainIntent= new Intent(QRScanActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
