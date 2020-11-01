package com.pfare.pfarepassenger.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pfare.pfarepassenger.R;

public class LoginActivity extends AppCompatActivity {


    private TextView textViewSignUp, textViewForgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialising textview

        textViewSignUp = (TextView)findViewById(R.id.textview_signup);
        textViewForgotPassword = (TextView)findViewById(R.id.textview_forgot_password);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Please Sign Up", Toast.LENGTH_LONG).show();
            }
        });
    }
}
