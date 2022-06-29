package com.student.council.electionSystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.student.council.electionSystem.R;

public class Admin_select extends AppCompatActivity {
    private Button individualvote, election, candidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_select);


        individualvote=(Button)findViewById(R.id.indvote);
        election=(Button)findViewById(R.id.eletime);
        candidate= (Button)findViewById(R.id.ctime);

        individualvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_select.this,IndividualVotesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        election.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_select.this,AdminElectionTime.class);
                startActivity(intent);
                finish();
            }
        });

        candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_select.this,AdminCandidateTime.class);
                startActivity(intent);
                finish();
            }
        });


    }
}