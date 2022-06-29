package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.student.council.electionSystem.R;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {
    EditText inputnumber1,inputnumber2,inputnumber3,inputnumber4,inputnumber5,inputnumber6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        Button verifybuttonclick = findViewById(R.id.buttongetotp);
        final String[] getOtpBackend = new String[1];

        inputnumber1=findViewById(R.id.inputotp1);
        inputnumber2=findViewById(R.id.inputotp2);
        inputnumber3=findViewById(R.id.inputotp3);
        inputnumber4=findViewById(R.id.inputotp4);
        inputnumber5=findViewById(R.id.inputotp5);
        inputnumber6=findViewById(R.id.inputotp6);

        getOtpBackend[0] = getIntent().getStringExtra("backendotp");


        verifybuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputnumber1.getText().toString().trim().isEmpty() && !inputnumber2.getText().toString().trim().isEmpty() && !inputnumber3.getText().toString().trim().isEmpty() && !inputnumber4.getText().toString().trim().isEmpty() && !inputnumber5.getText().toString().trim().isEmpty() && !inputnumber6.getText().toString().trim().isEmpty()){
                    String enttcodeotp = inputnumber1.getText().toString()+
                            inputnumber2.getText().toString()+
                            inputnumber3.getText().toString()+
                            inputnumber4.getText().toString()+
                            inputnumber5.getText().toString()+
                            inputnumber6.getText().toString();
                    if(getOtpBackend[0] !=null){
                        verifybuttonclick.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtpBackend[0],enttcodeotp);

                        FirebaseAuth.getInstance().signInWithCredential(
                                phoneAuthCredential
                        ).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        verifybuttonclick.setVisibility(View.VISIBLE);
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(getApplicationContext(),CandidateRegistration.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(OtpVerificationActivity.this,"Enter correct OTP",Toast.LENGTH_SHORT);
                                        }
                                    }
                                }
                        );

                    }else {
                        Toast.makeText(OtpVerificationActivity.this,"Please check your internet connection..!",Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(OtpVerificationActivity.this,"Plese enter all number",Toast.LENGTH_SHORT).show();
                }
            }
        });

         numberotpmove();


         TextView resendLabel = findViewById(R.id.textresendotp);
         resendLabel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 PhoneAuthProvider.getInstance().verifyPhoneNumber(
                         "+91" + getIntent().getStringExtra("mobile"),
                         60,
                         TimeUnit.SECONDS,
                         OtpVerificationActivity.this,
                         new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                             @Override
                             public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                             }

                             @Override
                             public void onVerificationFailed(@NonNull FirebaseException e) {

                                 Toast.makeText(OtpVerificationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                 getOtpBackend[0] = newbackendotp;
                                 Toast.makeText(OtpVerificationActivity.this,"Otp sent..",Toast.LENGTH_SHORT);
                             }
                         }
                 );
             }
         });
    }

    private void numberotpmove() {
        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}