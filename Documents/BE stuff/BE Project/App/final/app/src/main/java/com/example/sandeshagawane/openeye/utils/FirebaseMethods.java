package com.example.sandeshagawane.openeye.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

/**
 * Created by sandeshagawane on 06/03/18.
 */

public class FirebaseMethods {

    private Context mContext;
    private String userID;
    private FirebaseAuth mAuth;

    private static final String TAG = "FirebaseMethods";

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public static boolean checkIfuserIDExists(String userID, DataSnapshot dataSnapshot){
        Log.d(TAG,"checkIfuserIDExists: checking if" + userID + "already exists");

        NewUserDatabaseAdapter user = new NewUserDatabaseAdapter();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            Log.d(TAG,"checkIfuserIDExists: datasnapshot: " + ds);

            user.setUserID(ds.getValue(NewUserDatabaseAdapter.class).getUserID());
            Log.d(TAG, "checkIfUsernameExists: userID: " + user.getUserID());

            if(user.getUserID().equals(userID)){
                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUserID());
                return true;
            }

        }
        return true;
    }

}
