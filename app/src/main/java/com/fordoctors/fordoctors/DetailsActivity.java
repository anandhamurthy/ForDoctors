package com.fordoctors.fordoctors;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fordoctors.fordoctors.adapter.DetailsAdapter;
import com.fordoctors.fordoctors.model.Prescription;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    private TextView Details_Name, Details_Email, Details_Phone_Number, Details_Age, Details_Timestamp, Details_Gender, Details_Address, Details_Details;
    private RecyclerView Medicine_List;
    private CircleImageView Details_Profile_Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Prescription prescription = (Prescription) bundle.getSerializable("prescription");


        Details_Name=findViewById(R.id.details_name);
        Details_Email=findViewById(R.id.details_email);
        Details_Phone_Number=findViewById(R.id.details_phone_number);
        Details_Age=findViewById(R.id.details_age);
        Details_Gender=findViewById(R.id.details_gender);
        Details_Address=findViewById(R.id.details_address);
        Details_Details=findViewById(R.id.details_details);
        Details_Timestamp=findViewById(R.id.details_timestamp);
        Medicine_List=findViewById(R.id.details_prescriptions_list);
        Details_Profile_Image=findViewById(R.id.details_profile_image);

        Details_Name.setText(prescription.getName());
        Details_Age.setText(prescription.getAge());
        Details_Gender.setText(prescription.getGender());
        Details_Phone_Number.setText(prescription.getPhone_number());
        Details_Address.setText(prescription.getAddress()+", "+prescription.getCity()+", "+prescription.getState()+", "+prescription.getCountry());
        Details_Email.setText(prescription.getEmail());
        Details_Timestamp.setText(prescription.getTimestamp());
        Details_Details.setText(prescription.getDetails());
         Glide.with(getApplicationContext()).load(prescription.getProfile_image()).
                placeholder(R.drawable.profile_placeholder).into(Details_Profile_Image);

        Medicine_List.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        Medicine_List.setLayoutManager(mLayoutManager);
        DetailsAdapter myStatusAdapter = new DetailsAdapter(DetailsActivity.this, prescription.getMedicine());
        Medicine_List.setAdapter(myStatusAdapter);


    }

}