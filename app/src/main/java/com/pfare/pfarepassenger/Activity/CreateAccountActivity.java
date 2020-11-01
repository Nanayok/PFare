package com.pfare.pfarepassenger.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pfare.pfarepassenger.R;
import com.pfare.pfarepassenger.Utils.Connectivity;
import com.pfare.pfarepassenger.Utils.Constants;
import com.pfare.pfarepassenger.Utils.PasswordStrength;
import com.pfare.pfarepassenger.models.User;

import org.json.JSONException;

public class CreateAccountActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    private String firstName, lastName, email, password, phoneNumber;

    private TextView textView_enter_password_text_verification;
    private KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra(Constants.KEY_PHONE_NUMBER);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        editTextFirstName = (EditText)findViewById(R.id.edittext_firstname) ;
        editTextLastName = (EditText)findViewById(R.id.edittext_lastname) ;
        editTextEmail = (EditText)findViewById(R.id.edittext_email);
        editTextPassword = (EditText)findViewById(R.id.edittext_password);

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (s.toString().length() > 0) {
//
//                    //buttonSignUp.setBackgroundColor(getResources().getColor(R.color.green_color));
//                    buttonRegister.setBackground(getResources().getDrawable(R.drawable.button_background));
//
//                }else{
//                    buttonRegister.setBackground(getResources().getDrawable(R.drawable.button_grey_background));
//
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final ImageView imageViewConfirm = (ImageView)findViewById(R.id.image_view_confirm);
        imageViewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
                    editTextPassword.setTransformationMethod(new SingleLineTransformationMethod());

                    imageViewConfirm.setImageResource(R.drawable.ic_visibility_black_24dp);
                }
                else {
                    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    imageViewConfirm.setImageResource(R.drawable.ic_visibility_off_black_24dp);

                }

                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });


        textView_enter_password_text_verification = (TextView) findViewById(R.id.enter_password_text_verification);


//        firstName = editTextFirstName.getText().toString();
//        lastName = editTextLastName.getText().toString();
//        email = editTextEmail.getText().toString();

//        Log.d("firstname", firstName);
//        Log.d("lastname", lastName);
//        Log.d("email", email);



        Button buttonCreateAccount= (Button)findViewById(R.id.button_create_account);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // writeNewUser("Nana", "Koranteng", "nana@yahoo.com" , "233243314725");

                next();

            }
        });
    }

    private void writeNewUser(String firstName, String lastName, String phoneNumber, String email, String password) {
        User user = new User(firstName, lastName, phoneNumber, email, password);

//        mDatabase.child("users").child(userId).setValue(user);
        mDatabase.child("users").push().setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        hud.dismiss();
                        Toast.makeText(CreateAccountActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(CreateAccountActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        hud.dismiss();
                        Toast.makeText(CreateAccountActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();

                    }
                });


    }

    private void next() {


        if(!validateFirstName()){
            return;
        }

        if(!validateLastName()){
            return;
        }

        if(!validateEmail()){
            return;
        }

        if(!validatePassword()){
            return;
        }

        if(!(Connectivity.getInstance(this).isOnline())) {

            Toast.makeText(CreateAccountActivity.this,R.string.noInternetAvailable, Toast.LENGTH_SHORT).show();


        }
//        else {
//
//            Toast.makeText(this,"You are not online!!!!",8000).show();
//            Log.v("Home", "############################You are not online!!!!");
//        }

//        try {
//
//            requestOTP();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        firstName = editTextFirstName.getText().toString().trim();
        lastName = editTextLastName.getText().toString().trim();
        email =editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        Log.d("firstName", firstName);
        Log.d("lastName", lastName);
        Log.d("email", email);
        Log.d("password", password);



//
//        Intent intent  = new Intent(CreateAccountActivity.this, BankDetailsActivity.class);
//                intent.putExtra(Constants.KEY_FIRST_NAME, firstName);
//        intent.putExtra(Constants.KEY_LAST_NAME, lastName);
//        intent.putExtra(Constants.KEY_COUNTRY, country);
//        intent.putExtra(Constants.KEY_STATE, state);
//        intent.putExtra(Constants.KEY_CITY, city);
//        intent.putExtra(Constants.KEY_STREET, street);
//        intent.putExtra(Constants.KEY_ZIP, zip);
//        intent.putExtra(Constants.KEY_PASSWORD, password);
//        intent.putExtra(Constants.KEY_EMAIL, email);
//        intent.putExtra(Constants.KEY_PHONE_NUMBER, phoneNumber);
//                startActivity(intent);


//        try {
//
//            registerUser();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        registerUser();



    }

    private boolean validateFirstName() {
        if (editTextFirstName.getText().toString().trim().isEmpty()) {
//            inputLayoutFirstName.setError(getString(R.string.err_msg_first_name));

            MaterialDialog dialog = new MaterialDialog.Builder(CreateAccountActivity.this)
                    .title("Alert")
                    .content(R.string.err_msg_first_name)
                    .positiveText("Ok")
                    //.negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                            dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                        }
                    })
                    .show();

            requestFocus(editTextFirstName);
            return false;
        } else {
            //inputLayoutFirstName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLastName() {
        if (editTextLastName.getText().toString().trim().isEmpty()) {
//            inputLayoutFirstName.setError(getString(R.string.err_msg_first_name));

            //Toast.makeText(SignUpSecondActivity.this, R.string.err_msg_last_name, Toast.LENGTH_LONG ).show();
            MaterialDialog dialog = new MaterialDialog.Builder(CreateAccountActivity.this)
                    .title("Alert")
                    .content(R.string.err_msg_last_name)
                    .positiveText("Ok")
                    //.negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                            dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                        }
                    })
                    .show();


            requestFocus(editTextLastName);
            return false;
        } else {
            //inputLayoutFirstName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        if (editTextEmail.getText().toString().trim().isEmpty()) {
//            inputLayoutFirstName.setError(getString(R.string.err_msg_first_name));

            MaterialDialog dialog = new MaterialDialog.Builder(CreateAccountActivity.this)
                    .title("Alert")
                    .content(R.string.err_msg_email)
                    .positiveText("Ok")
                    //.negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                            dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                        }
                    })
                    .show();

            requestFocus(editTextEmail);
            return false;
        } else {
            //inputLayoutFirstName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
//        if ((editTextPassword.getText().toString().trim().isEmpty()) || ((editTextPassword.length() < 8) || !(textView_enter_password_text_verification.getText().toString().equals("Very Strong")))) {
        if ((editTextPassword.getText().toString().trim().isEmpty()) || ((editTextPassword.length() < 8) || !(textView_enter_password_text_verification.getText().toString().equals("Very Strong")))) {

            // inputLayoutPassword.setError(getString(R.string.err_msg_password));
            // Toast.makeText(SignUpSecondActivity.this,R.string.err_msg_password, Toast.LENGTH_LONG).show();

            MaterialDialog dialog = new MaterialDialog.Builder(CreateAccountActivity.this)
                    .title("Alert")
                    .content(R.string.err_msg_password)
                    .positiveText("Ok")
                    //.negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                            dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                        }
                    })
                    .show();

            requestFocus(editTextPassword);
            return false;
        } else {
            // inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void calculatePasswordStrength(String str) {
        // Now, we need to define a PasswordStrength enum
        // with a calculate static method returning the password strength
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        textView_enter_password_text_verification.setText(passwordStrength.msg);
        //root.setBackgroundColor(passwordStrength.color);
    }


    public void registerUser(){

                hud = KProgressHUD.create(CreateAccountActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Registration in progress")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        writeNewUser( firstName, lastName, phoneNumber , email, password);

    }


}
