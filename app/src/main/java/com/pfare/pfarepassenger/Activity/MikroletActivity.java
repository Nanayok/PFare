package com.pfare.pfarepassenger.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.pfare.pfarepassenger.R;

public class MikroletActivity extends AppCompatActivity {

    final int GET_NEW_CARD = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mikrolet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Mikrolet");
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.bringToFront();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                finish();
            }
        });

        Button buttonPayment = (Button)findViewById(R.id.button_pay_fare);
        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MikroletActivity.this, CardEditActivity.class);
                startActivityForResult(intent,GET_NEW_CARD);
            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            // Your processing goes here.

            Toast.makeText(this, "Payment Received", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

}
