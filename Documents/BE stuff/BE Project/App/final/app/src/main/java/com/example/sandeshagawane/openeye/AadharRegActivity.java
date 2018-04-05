package com.example.sandeshagawane.openeye;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandeshagawane.openeye.utils.DataAttributes;
import com.example.sandeshagawane.openeye.utils.NewUserDatabaseAdapter;
import com.example.sandeshagawane.openeye.utils.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class AadharRegActivity extends AppCompatActivity {
    // variables to store extracted xml data
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode,MobileNo,email_ID;

    // UI Elements
    public static TextView tv_sd_uid,tv_sd_name,tv_sd_gender,tv_sd_yob,tv_sd_co,tv_sd_vtc,tv_sd_po,tv_sd_dist,
            tv_sd_state,tv_sd_pc;

    private EditText voterId,mobileNo,email,password,confPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private Button QRScanBtn,RegBtn;
    private FloatingActionButton BacktoReg;
    private ProgressBar AadharRegProgress;
    String user_id;

    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_reg);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = FirebaseAuth.getInstance().getUid();


        AadharRegProgress= (ProgressBar)findViewById(R.id.uid_reg_progress);

        tv_sd_uid = (TextView)findViewById(R.id.editUid);
        tv_sd_name = (TextView)findViewById(R.id.editName);
        tv_sd_gender = (TextView)findViewById(R.id.editGender);
        tv_sd_yob = (TextView)findViewById(R.id.editYOB);
        tv_sd_co = (TextView)findViewById(R.id.editCO);
        tv_sd_vtc = (TextView)findViewById(R.id.editVTC);
        tv_sd_po = (TextView)findViewById(R.id.editPO);
        tv_sd_dist = (TextView)findViewById(R.id.editDist);
        tv_sd_state = (TextView)findViewById(R.id.editState);
        tv_sd_pc = (TextView)findViewById(R.id.editPC);
        BacktoReg=(FloatingActionButton) findViewById(R.id.BackToMReg);
        QRScanBtn=findViewById(R.id.QRScanBtn);
        RegBtn=(Button)findViewById(R.id.uid_reg_btn);

        //Edit text that available to user for fill details
        email=(EditText)findViewById(R.id.editEmail);
        voterId=(EditText)findViewById(R.id.editVoterID);
        mobileNo=(EditText)findViewById(R.id.editMobile);
        password=(EditText)findViewById(R.id.editPassword);
        confPassword=(EditText)findViewById(R.id.editCPassword);


        //Registration Processs Creating Account on Firebase
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Email = email.getText().toString();
                final String pass = password.getText().toString();
                final String confirm_pass= confPassword.getText().toString();
                final String UID = tv_sd_uid.getText().toString();
                final String Name = tv_sd_name.getText().toString();
                final String Gender = tv_sd_gender.getText().toString();
                final String Mobile = mobileNo.getText().toString();
                final String voterID = voterId.getText().toString();


                if(!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass) &&!TextUtils.isEmpty(Mobile) && !TextUtils.isEmpty(voterID)){

                    if(!TextUtils.isEmpty(UID) && !TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Gender)){
                        //Scan data store to firebase database


                            if(pass.equals(confirm_pass)){

                                AadharRegProgress.setVisibility(View.VISIBLE);
                                mAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {

                                            storeFirestore();
                                            addUser();

                                            Intent setupIntent = new Intent(AadharRegActivity.this, SetupActivity.class);
                                            startActivity(setupIntent);
                                            finish();
                                            Toast.makeText(AadharRegActivity.this, "New Account created Succsessfully", Toast.LENGTH_LONG).show();

                                        } else {

                                            String errorMessage = task.getException().getMessage();
                                            Toast.makeText(AadharRegActivity.this, "Error :" + errorMessage, Toast.LENGTH_LONG).show();
                                        }

                                        AadharRegProgress.setVisibility(View.INVISIBLE);

                                    }
                                });


                            }else {

                                Toast.makeText(AadharRegActivity.this, "Confirm Password and Password Field doesn't match.", Toast.LENGTH_LONG).show();

                            }

                    }else{
                        Toast.makeText(AadharRegActivity.this,"Please Scan the Aadhaar QR ",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(AadharRegActivity.this,"Please fill the details",Toast.LENGTH_LONG).show();
                }

             }
         });



        //int Storage
        storage = new Storage(this);


        BacktoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BacktoRegIntent = new Intent(AadharRegActivity.this, RegisterActivity.class);
                startActivity(BacktoRegIntent);
                finish();
            }
        });



    }
    private void storeFirestore() {



        String uid = tv_sd_uid.getText().toString();
        String name = tv_sd_name.getText().toString();
        String gender = tv_sd_gender.getText().toString();
        String YOB = tv_sd_yob.getText().toString();
        String careOf = tv_sd_co.getText().toString();
        String VTC = tv_sd_vtc.getText().toString();
        String dist = tv_sd_dist.getText().toString();
        String state = tv_sd_state.getText().toString();
        String postOffice = tv_sd_po.getText().toString();
        String postCode = tv_sd_pc.getText().toString();
        String Mobile = mobileNo.getText().toString();
        String Email = email.getText().toString();

        Map< String, String > UserData = new HashMap< >();

        UserData.put("uid",uid);
        UserData.put("name",name);
        UserData.put("gender",gender);
        UserData.put("yob",YOB);
        UserData.put("careof", careOf);
        UserData.put("vtc", VTC);
        UserData.put("dist", dist);
        UserData.put("state",state);
        UserData.put("postoffice",postOffice);
        UserData.put("postcode",postCode);
        UserData.put("mobileno", Mobile);
        UserData.put("email", Email);

        firebaseFirestore.collection("aadhaar_data").document("users").set(UserData).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                    Toast.makeText(AadharRegActivity.this, "Error"+e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    //Scan data store to firebase database
    private void addUser() {

        String uid = tv_sd_uid.getText().toString().trim();
        String name = tv_sd_name.getText().toString().trim();
        String gender = tv_sd_gender.getText().toString().trim();
        String YOB = tv_sd_yob.getText().toString().trim();
        String careOf = tv_sd_co.getText().toString().trim();
        String VTC = tv_sd_vtc.getText().toString().trim();
        String dist = tv_sd_dist.getText().toString().trim();
        String state = tv_sd_state.getText().toString().trim();
        String postOffice = tv_sd_po.getText().toString().trim();
        String postCode = tv_sd_pc.getText().toString().trim();
        String Mobile = mobileNo.getText().toString().trim();
        String Email = email.getText().toString().trim();

        if(!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(YOB) && !TextUtils.isEmpty(careOf) && !TextUtils.isEmpty(dist) && !TextUtils.isEmpty(VTC)&& !TextUtils.isEmpty(state) && !TextUtils.isEmpty(postCode) && !TextUtils.isEmpty(Mobile) && !TextUtils.isEmpty(Email)){

            //IF we want to generate new automatic key
            //String id = mDatabaseUser.push().getKey();

            NewUserDatabaseAdapter User = new NewUserDatabaseAdapter(uid,uid,name,gender,YOB,careOf,VTC,dist,state,postCode,postOffice,Mobile,Email);

            mDatabaseUser.child(uid).setValue(User);


        }else {
            Toast.makeText(this,"Database store Error :",Toast.LENGTH_LONG).show();
        }


    }


    /**
     * onclick handler for scan new card
     * @param view
     */
    public void scanNow( View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(AadharRegActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AadharRegActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(AadharRegActivity.this,new String[]{Manifest.permission.CAMERA},1);
            }else{
                Toast.makeText(AadharRegActivity.this,"You already have Permission",Toast.LENGTH_LONG).show();
            }
        }
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a Aadharcard QR Code");
        integrator.setPrompt("Volume keys to Enable/Disable Flash");
        integrator.setResultDisplayDuration(500);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * function handle scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // process received data
            if(scanContent != null && !scanContent.isEmpty()){
                processScannedData(scanContent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Scan Cancelled", Toast.LENGTH_SHORT);
                toast.show();
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * process xml string received from aadhaar card QR code
     * @param scanData
     */
    protected void processScannedData(String scanData){
        Log.d("Rajdeol",scanData);
        XmlPullParserFactory pullParserFactory;

        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));

            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("Rajdeol","Start document");
                } else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    uid = parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR);
                    //name
                    name = parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR);
                    //gender
                    gender = parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR);
                    // year of birth
                    yearOfBirth = parser.getAttributeValue(null,DataAttributes.AADHAR_YOB_ATTR);
                    // care of
                    careOf = parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR);
                    // village Tehsil
                    villageTehsil = parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR);
                    // Post Office
                    postOffice = parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR);
                    // district
                    district = parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR);
                    // state
                    state = parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR);
                    // Post Code
                    postCode = parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR);

                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d("Rajdeol","End tag "+parser.getName());

                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d("Rajdeol","Text "+parser.getText());

                }
                // update eventType
                eventType = parser.next();
            }

            // display the data on screen
            displayScannedData();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }// EO function

    /**
     * show scanned information
     */
    public void displayScannedData(){
        //ll_data_wrapper.setVisibility(View.GONE);
        //ll_scanned_data_wrapper.setVisibility(View.VISIBLE);
        //ll_action_button_wrapper.setVisibility(View.VISIBLE);

        // clear old values if any
        tv_sd_uid.setText("");
        tv_sd_name.setText("");
        tv_sd_gender.setText("");
        tv_sd_yob.setText("");
        tv_sd_co.setText("");
        tv_sd_vtc.setText("");
        tv_sd_po.setText("");
        tv_sd_dist.setText("");
        tv_sd_state.setText("");
        tv_sd_pc.setText("");

        // update UI Elements
        tv_sd_uid.setText(uid);
        tv_sd_name.setText(name);
        tv_sd_gender.setText(gender);
        tv_sd_yob.setText(yearOfBirth);
        tv_sd_co.setText(careOf);
        tv_sd_vtc.setText(villageTehsil);
        tv_sd_po.setText(postOffice);
        tv_sd_dist.setText(district);
        tv_sd_state.setText(state);
        tv_sd_pc.setText(postCode);
    }

    /**
     * save data to storage
     */
    public void saveData(View view){
        // We are going to use json to save our data
        // create json object
        JSONObject aadhaarData = new JSONObject();
        try {
            aadhaarData.put(DataAttributes.AADHAR_UID_ATTR, uid);

            if(name == null){name = "";}
            aadhaarData.put(DataAttributes.AADHAR_NAME_ATTR, name);

            if(gender == null){gender = "";}
            aadhaarData.put(DataAttributes.AADHAR_GENDER_ATTR, gender);

            if(yearOfBirth == null){yearOfBirth = "";}
            aadhaarData.put(DataAttributes.AADHAR_YOB_ATTR, yearOfBirth);

            if(careOf == null){careOf = "";}
            aadhaarData.put(DataAttributes.AADHAR_CO_ATTR, careOf);

            if(villageTehsil == null){villageTehsil = "";}
            aadhaarData.put(DataAttributes.AADHAR_VTC_ATTR, villageTehsil);

            if(postOffice == null){postOffice = "";}
            aadhaarData.put(DataAttributes.AADHAR_PO_ATTR, postOffice);

            if(district == null){district = "";}
            aadhaarData.put(DataAttributes.AADHAR_DIST_ATTR, district);

            if(state == null){state = "";}
            aadhaarData.put(DataAttributes.AADHAR_STATE_ATTR, state);

            if(postCode == null){postCode = "";}
            aadhaarData.put(DataAttributes.AADHAR_PC_ATTR, postCode);

            // read data from storage
            String storageData = storage.readFromFile();

            JSONArray storageDataArray;
            //check if file is empty
            if(storageData.length() > 0){
                storageDataArray = new JSONArray(storageData);
            }else{
                storageDataArray = new JSONArray();
            }


            // check if storage is empty
            if(storageDataArray.length() > 0){
                // check if data already exists
                for(int i = 0; i<storageDataArray.length();i++){
                    String dataUid = storageDataArray.getJSONObject(i).getString(DataAttributes.AADHAR_UID_ATTR);
                    if(uid.equals(dataUid)){
                        // do not save anything and go back
                        // show home screen
                       // tv_cancel_action.performClick();

                        return;
                    }
                }
            }
            // add the aadhaar data
            storageDataArray.put(aadhaarData);
            // save the aadhaardata
            storage.writeToFile(storageDataArray.toString());

            // show home screen
            //tv_cancel_action.performClick();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
