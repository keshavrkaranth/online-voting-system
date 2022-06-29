package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText Name,Password,Phone;
    private Button Register;
    private DatabaseReference mref;

    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name=(EditText)findViewById(R.id.nameentry);
        Password=(EditText)findViewById(R.id.passwordentry);
        Phone=(EditText)findViewById(R.id.phoneentry);
        Register=(Button)findViewById(R.id.registerbutton);
        mref= FirebaseDatabase.getInstance().getReference();
        LoadingBar=new ProgressDialog(this);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(Name.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your name first..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Phone.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your Phone Number first..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your password first..", Toast.LENGTH_SHORT).show();
                }
                else if(Phone.getText().toString().length()<10)
                {
                    Toast.makeText(RegisterActivity.this, "Please enter correct Phone Number..", Toast.LENGTH_SHORT).show();

                }
                else if(!validateMobile(Phone.getText().toString()))
                {
                        Toast.makeText(RegisterActivity.this, "Please enter correct Phone Number..", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!(dataSnapshot.child("Users").child(Phone.getText().toString()).exists())) //"+91"+
                            {
                                CreateAccount(Phone,Name,Password);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Account with this number already exist..", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
    }

    private void CreateAccount(EditText phone, EditText name, EditText password) {
        Map<String,Object> details=new HashMap<String,Object>();
        details.put("Phone",phone.getText().toString()); //"+91"+
        details.put("Name",name.getText().toString());
        details.put("Password",password.getText().toString());
        details.put("Vote","0");
        details.put("Party","");
        details.put("ID","");
        details.put("Computer Science","0");
        details.put("Information Technology","0");
        details.put("Electrical","0");
        details.put("Electronics","0");
        details.put("Mechanical","0");
        mref.child("Users").child(phone.getText().toString()).updateChildren(details) //"+91"+
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, "Account created successfully..", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    boolean validateMobile(String input) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public void gotologinpage(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
