package com.example.sandeshagawane.openeye.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sandeshagawane.openeye.AadharRegActivity;
import com.example.sandeshagawane.openeye.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Adapter class to inflate saved Aadharcard list
 * Created by RajinderPal on 6/22/2016.
 */
public class CardListAdapter extends ArrayAdapter<ArrayList> {

    // UI Elements
    TextView tv_sd_uid,tv_sd_name,tv_sd_gender,tv_sd_yob,tv_sd_co,tv_sd_vtc,tv_sd_po,tv_sd_dist,
            tv_sd_state,tv_sd_pc,tv_delete_card;

    // variables to contain extracted values
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;

    private final AadharRegActivity context;
    private final ArrayList<JSONObject> values;

    public CardListAdapter(AadharRegActivity context, ArrayList values){
        super(context,-1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        //inflate the row with layout
        View rowView = inflater.inflate(R.layout.activity_aadhar_reg,parent,false);

        // initiate UI elements
        tv_sd_uid = (TextView)rowView.findViewById(R.id.editUid);
        tv_sd_name = (TextView)rowView.findViewById(R.id.editName);
        tv_sd_gender = (TextView)rowView.findViewById(R.id.editGender);
        tv_sd_yob = (TextView)rowView.findViewById(R.id.editYOB);
        tv_sd_co = (TextView)rowView.findViewById(R.id.editCO);
        tv_sd_vtc = (TextView)rowView.findViewById(R.id.editVTC);
        tv_sd_po = (TextView)rowView.findViewById(R.id.editPO);
        tv_sd_dist = (TextView)rowView.findViewById(R.id.editDist);
        tv_sd_state = (TextView)rowView.findViewById(R.id.editState);
        tv_sd_pc = (TextView)rowView.findViewById(R.id.editPC);
        //tv_delete_card = (TextView)rowView.findViewById(R.id.tv_delete_card);


        // populate UI elements
        try {
            JSONObject jObj = values.get(position);
            uid = jObj.getString(DataAttributes.AADHAR_UID_ATTR);
            name = jObj.getString(DataAttributes.AADHAR_NAME_ATTR);
            gender = jObj.getString(DataAttributes.AADHAR_GENDER_ATTR);
            yearOfBirth = jObj.getString(DataAttributes.AADHAR_YOB_ATTR);
            careOf = jObj.getString(DataAttributes.AADHAR_CO_ATTR);
            villageTehsil = jObj.getString(DataAttributes.AADHAR_VTC_ATTR);
            postOffice = jObj.getString(DataAttributes.AADHAR_PO_ATTR);
            district = jObj.getString(DataAttributes.AADHAR_DIST_ATTR);
            state = jObj.getString(DataAttributes.AADHAR_STATE_ATTR);
            postCode = jObj.getString(DataAttributes.AADHAR_PC_ATTR);
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

            /*tv_delete_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jObj = values.get(position);
                    try {
                        String cardUid = jObj.getString(DataAttributes.AADHAR_UID_ATTR);
                        context.deleteCard(cardUid);
                        values.remove(position);
                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rowView;
    }
}
