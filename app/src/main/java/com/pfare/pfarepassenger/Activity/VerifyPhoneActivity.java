package com.pfare.pfarepassenger.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bigbangbutton.editcodeview.EditCodeListener;
import com.bigbangbutton.editcodeview.EditCodeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pfare.pfarepassenger.R;
import com.pfare.pfarepassenger.Utils.Constants;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String mVerificationId, mobile, enteredCode;



    //The edittext to input the code
    //private EditText editTextCode;

    //firebase auth object
    private FirebaseAuth mAuth;

    EditCodeView editCodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        editCodeView = (EditCodeView) findViewById(R.id.edit_code);

        //getting mobile number from the previous activity
        //and sending the verification code to the number
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        editCodeView.setEditCodeListener(new EditCodeListener() {
            @Override
            public void onCodeReady(String code) {
                // ...
                //Toast.makeText(OTPActivity.this, code, Toast.LENGTH_LONG).show();

                enteredCode = code;


//                try {
//
//                    verifyOTP();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                if(enteredCode.length() > 3) {
//
//                    buttonOTP.setBackground(getResources().getDrawable(R.drawable.button_background));
//                }else if(enteredCode.length() <4){
//                    buttonOTP.setBackground(getResources().getDrawable(R.drawable.button_grey_background));
//                }



            }
        });
    }


    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                //"+233" + mobile,
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);


    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
//                editTextCode.setText(code);
                editCodeView.setCode(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
//                            Intent intent = new Intent(VerifyPhoneActivity.this, DashboardActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);



                            Toast.makeText(VerifyPhoneActivity.this,"Verification Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(VerifyPhoneActivity.this, CreateAccountActivity.class);
                            intent.putExtra(Constants.KEY_PHONE_NUMBER, mobile);
                            startActivity(intent);

                            //Toast.makeText(VerifyPhoneActivity.this, "Authentication Successfull", Toast.LENGTH_LONG).show();

                            finish();



//                            new MaterialDialog.Builder(VerifyPhoneActivity.this)
//                                    .title(R.string.enter_full_name)
//                                    //.content(R.string.input_content)
//                                    //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                                    .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                                        @Override
//                                        public void onInput(MaterialDialog dialog, CharSequence input) {
//                                            // Do something
//
//                                            name=input.toString();
//
////                                            Toast.makeText(VerifyPhoneActivity.this, name,Toast.LENGTH_LONG).show();
////                                            checkInput();
////
////                                            //showToast("Prompt checked? " + dialog.isPromptCheckBoxChecked());
////                                            //Toast.makeText(TelematicsActivity.this,String.valueOf(dialog.isPromptCheckBoxChecked()),Toast.LENGTH_LONG).show();
////
////                                            fleet = String.valueOf(dialog.isPromptCheckBoxChecked());
//
//                                            saveUser();
//                                        }
//                                    })
//                                    //.checkBoxPromptRes(R.string.fleet, false, null).
//
//
//                                    .show();




                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            //Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }


}
