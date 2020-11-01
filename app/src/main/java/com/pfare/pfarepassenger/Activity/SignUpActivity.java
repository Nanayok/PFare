package com.pfare.pfarepassenger.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pfare.pfarepassenger.R;
import com.rilixtech.CountryCodePicker;

public class SignUpActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    private EditText editTextMobile;
    private Button buttonCreateAccount;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitle("Settings");
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        editTextMobile = (EditText)findViewById(R.id.editTextMobile);
        buttonCreateAccount=findViewById(R.id.button_verify_phone);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {

            // Initialize Firebase Auth
            //mAuth = FirebaseAuth.getInstance();
            // [END initialize_auth]


            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();

                phoneNumber = ccp.getSelectedCountryCodeWithPlus()+mobile;

                //Toast.makeText(MainActivity.this, phoneNumber ,Toast.LENGTH_LONG).show();

                if(mobile.isEmpty() || mobile.length() < 8){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                //verifyPhoneNumber();

                Intent intent = new Intent(SignUpActivity.this, VerifyPhoneActivity.class);
                //intent.putExtra("mobile", mobile);
                intent.putExtra("mobile", phoneNumber);
                startActivity(intent);

            }
        });

    }
}
