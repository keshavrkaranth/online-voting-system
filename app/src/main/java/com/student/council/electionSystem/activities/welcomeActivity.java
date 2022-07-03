package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.Model.VoteTimeSet;
import com.student.council.electionSystem.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class welcomeActivity extends AppCompatActivity {

    private Button Login,Register,cButton;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Login=(Button)findViewById(R.id.login);
        Register=(Button)findViewById(R.id.register);
        cButton = (Button)findViewById(R.id.register2);

        mref= FirebaseDatabase.getInstance().getReference();

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mref = FirebaseDatabase.getInstance().getReference("candidateTiming");
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String start_date = snapshot.child("start_date").getValue().toString();
                        String end_date = snapshot.child("end_date").getValue().toString();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
                        LocalDateTime start = LocalDateTime.parse(start_date.toString(),formatter);
                        LocalDateTime end = LocalDateTime.parse(end_date.toString(),formatter);
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println("check this"+end.compareTo(start));
                        System.out.println("check this1"+start.compareTo(now));
                        if((start.compareTo(now))==1 ||(end.compareTo(now))==1 ){
                            Intent intent = new Intent(welcomeActivity.this,CandiatephnoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(welcomeActivity.this,"Candidate registration not yet Started/Ended",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(welcomeActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcomeActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    public void aboutApp(View view) {
//        startActivity(new Intent(welcomeActivity.this, AboutAppActivity.class));
//    }
}
