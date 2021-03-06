package com.example.sandeshagawane.openeye;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private static final String FIRE_LOG = "FIRE_LOG";

    private CircleImageView default_profile;
    private Uri mainImageURI = null;
    private ProgressBar setupProgressbar;
    private boolean isChanged = false;
    private String user_id;

    private TextView SetupName,S_uid,S_careof,S_gender,S_yob,S_dist,S_state,S_postcode,S_email,S_mobile,S_vtc;
    private Button saveSettings;
    //public static Spinner ward,profession;
    //firebase
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseFirestore.collection("Aadhaar Data").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                String name = documentSnapshot.getString("name");
                String uid = documentSnapshot.getString("uid");
                String careof = documentSnapshot.getString("careof");
                String gender = documentSnapshot.getString("gender");
                String vtc = documentSnapshot.getString("vtc");
                String dist = documentSnapshot.getString("dist");
                String state = documentSnapshot.getString("state");
                String email = documentSnapshot.getString("email");
                String postcode= documentSnapshot.getString("postcode");

                SetupName.setText(name);
                S_uid.setText("Aadhaar No. : " + uid);
                S_careof.setText("Care Of : " + careof);
                S_gender.setText("Gender : " + gender);
                S_dist.setText("District : " + dist);
                S_email.setText("Email ID : "+email);
                S_vtc.setText("Village : " + vtc);
                S_state.setText("State : " + state);
                S_postcode.setText("Post Code : " + postcode);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        //toolbar
        android.support.v7.widget.Toolbar setupToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.setupToolbar);
        setSupportActionBar(setupToolBar);
        getSupportActionBar().setTitle("Account Setup");

        //UI elements
        default_profile =findViewById(R.id.default_profile_img);
        setupProgressbar = findViewById(R.id.setup_progressbar);
        saveSettings =findViewById(R.id.save_settings_btn);
        SetupName = findViewById(R.id.setup_name);
        S_uid = findViewById(R.id.s_uid);
        S_careof=findViewById(R.id.s_careof);
        S_dist=findViewById(R.id.s_dist);
        S_email=findViewById(R.id.s_email);
        S_gender=findViewById(R.id.s_gender);
        S_vtc=findViewById(R.id.s_vtc);
        S_postcode=findViewById(R.id.s_postcode);
        S_state=findViewById(R.id.s_state);

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = firebaseAuth.getCurrentUser().getUid();

        setupProgressbar.setVisibility(View.VISIBLE);
        saveSettings.setEnabled(false);

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    DocumentSnapshot documentSnapshot = task.getResult();

                    if(documentSnapshot.exists()){

                        //String name = task.getResult().getString("name");
                        String image = task.getResult().getString("image");

                        mainImageURI = Uri.parse(image);

                        //SetupName.setText(name);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.default_profile_male);

                        Glide.with(SetupActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(default_profile);


                    }

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this, "(FIRESTORE Retrieve Error) : " + error, Toast.LENGTH_LONG).show();

                }

                setupProgressbar.setVisibility(View.INVISIBLE);
                saveSettings.setEnabled(true);

            }
        });

        firebaseFirestore.collection("Aadhaar Data").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists() && documentSnapshot != null) {

                        String name = documentSnapshot.getString("name");

                        SetupName.setText(name);


                    }
                }else{

                    Log.d(FIRE_LOG,"Error"+ task.getException().getMessage());

                }

            }
        });

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user_name = SetupName.getText().toString();



                    setupProgressbar.setVisibility(View.VISIBLE);

                    if (isChanged) {

                        user_id = firebaseAuth.getCurrentUser().getUid();

                        StorageReference image_path = storageReference.child("profile_images").child(user_id + ".jpg");
                        image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()) {

                                    storeFirestore(task, user_name);

                                } else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(SetupActivity.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();

                                    setupProgressbar.setVisibility(View.INVISIBLE);

                                }
                            }
                        });

                    } else {

                        storeFirestore(null, user_name);

                    }

            }

        });

        default_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(SetupActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(SetupActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(SetupActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }

            }

        });


    }

    private void storeFirestore(@NonNull Task<UploadTask.TaskSnapshot> task, String user_name) {

        Uri download_uri;

        if(task != null) {

            download_uri = task.getResult().getDownloadUrl();

        } else {

            download_uri = mainImageURI;

        }

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", user_name);
        userMap.put("image", download_uri.toString());

        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(SetupActivity.this, "The user Settings are updated.", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

                }

                setupProgressbar.setVisibility(View.INVISIBLE);

            }
        });


    }

    private void BringImagePicker() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(SetupActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                default_profile.setImageURI(mainImageURI);

                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

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

                logOut_activity();

                return true;

            case R.id.action_home_btn:

                main_activity();

                return true;

            case R.id.action_myneta_btn:
                myneta_activity();

                return true;

            default:
                return false;
        }

    }

    /**
     * My neta Acitivity
     */
    private void myneta_activity() {
        Intent mynetaIntent = new Intent(SetupActivity.this,MyNetaActivity.class);
        startActivity(mynetaIntent);
        finish();
    }

    /**
     * Main Activity Home
     */
    private void main_activity() {
        Intent logoutIntent = new Intent(SetupActivity.this,MainActivity.class);
        startActivity(logoutIntent);
        finish();
    }

    /**
     * LOGOUT
     */
    private void logOut_activity() {
        firebaseAuth.signOut();
        logout();
    }

    private void logout() {
        Intent logoutIntent = new Intent(SetupActivity.this,LoginActivity.class);
        startActivity(logoutIntent);
        finish();

    }

}
