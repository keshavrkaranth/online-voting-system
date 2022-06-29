package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.R;

import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    TextView totatVote, jTotalVote, rTotalVote, dTotalVote, mTotalVote;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        totatVote = (TextView) findViewById(R.id.totalVote);
        jTotalVote = (TextView) findViewById(R.id.jtotalVote);
        rTotalVote = (TextView) findViewById(R.id.rtotalVote);
        dTotalVote = (TextView) findViewById(R.id.dtotalVote);
        mTotalVote = (TextView) findViewById(R.id.mtotalVote);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object vote = map.get("Vote");
                    int vValue = (int) Integer.parseInt(String.valueOf(vote));
                    sum += vValue;

                    totatVote.setText(String.valueOf(sum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int jsum = 0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object vote = map.get("Computer Science");
                    int vValue = (int) Integer.parseInt(String.valueOf(vote));
                    jsum += vValue;

                    jTotalVote.setText(String.valueOf(jsum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int rsum = 0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object vote = map.get("Information Technology");
                    int vValue = (int) Integer.parseInt(String.valueOf(vote));
                    rsum += vValue;

                    rTotalVote.setText(String.valueOf(rsum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int dsum = 0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object vote = map.get("Electrical");
                    int vValue = (int) Integer.parseInt(String.valueOf(vote));
                    dsum += vValue;

                    dTotalVote.setText(String.valueOf(dsum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int msum = 0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object vote = map.get("Electronics");
                    int vValue = (int) Integer.parseInt(String.valueOf(vote));
                    msum += vValue;

                    mTotalVote.setText(String.valueOf(msum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int msum = 0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object vote = map.get("Mechanical");
                    int vValue = (int) Integer.parseInt(String.valueOf(vote));
                    msum += vValue;

                    mTotalVote.setText(String.valueOf(msum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void individualVote(View view) {
        startActivity(new Intent(AdminActivity.this, Admin_select.class));
    }


    public void logoutFromAdmin(View view) {
        Intent intent=new Intent(AdminActivity.this,welcomeActivity.class);
        startActivity(intent);
        finish();
    }
}