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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.Model.Users;
import com.student.council.electionSystem.Prevalent.Prevalent;
import com.student.council.electionSystem.R;

public class LoginActivity extends AppCompatActivity {

    private EditText Phone,Password;
    private Button Login;
    private DatabaseReference mref;
    private ProgressDialog LoadingBar;
    String myphone,mypassword;
    Button Newuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Phone=(EditText)findViewById(R.id.loginphone);
        Password=(EditText)findViewById(R.id.loginpassword);
        Login=(Button)findViewById(R.id.loginbutton);
        Newuser=(Button) findViewById(R.id.Newuser);

        LoadingBar=new ProgressDialog(this);
        mref= FirebaseDatabase.getInstance().getReference();
        mypassword=Password.getText().toString();
        myphone=Phone.getText().toString(); //"*91"+


        Newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(Phone.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please enter your phone number..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please enter your password...", Toast.LENGTH_SHORT).show();
                }
                else if(Phone.getText().toString().length() <10)
                {
                    Toast.makeText(LoginActivity.this, "Please enter correct phone number..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoginUser();
                }
            }
        });
    }

    private void LoginUser() {



        LoadingBar.setTitle("Login Account");
        LoadingBar.setMessage("Please wait while we are checking our credentials..");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();

        AllowAccessToUser(myphone,mypassword);
    }

    private void AllowAccessToUser(final String myphone, final String mypassword) {

        if(Phone.getText().toString().equals("6361733187")&&Password.getText().toString().equals("roopeshkumar"))
        {
            LoadingBar.dismiss();
            Toast.makeText(LoginActivity.this, "Logged in Successfully..", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(LoginActivity.this,Admin_select.class);
            // i.putExtra("Name",userdata.getName());
            startActivity(i);
            finish();
        }
        else {

            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child("Users").child(Phone.getText().toString()).exists()) //"*91"+
                    {
                        final Users userdata=dataSnapshot.child("Users").child(Phone.getText().toString()).getValue(Users.class); //"*91"+
                        if(userdata.getPhone().equals(Phone.getText().toString()))  //"*91"+
                        {

                            if(userdata.getPassword().equals(Password.getText().toString()))
                            {

                                LoadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Logged in Successfully..", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                Prevalent.currentOnlineUser=userdata;
                                i.putExtra("phone",Phone.getText().toString());  //"*91"+
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                LoadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "please enter correct password..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "please create your account first with this number ..", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }
}
