package com.fordoctors.fordoctors;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class Login extends AppCompatActivity {

    private CountryCodePicker Login_Country_Code;
    private EditText Login_Phone_Number;
    private String Code="+91";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_Phone_Number = findViewById(R.id.login_phone_number);
        Login_Country_Code = findViewById(R.id.login_country_code);

        Login_Country_Code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                Code=Login_Country_Code.getSelectedCountryCodeWithPlus();
            }
        });

        findViewById(R.id.login_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               final String number = Login_Phone_Number.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    Login_Phone_Number.setError("Valid number is required");
                    Login_Phone_Number.requestFocus();
                    return;
                }
                Intent intent = new Intent(Login.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", Code+number);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
