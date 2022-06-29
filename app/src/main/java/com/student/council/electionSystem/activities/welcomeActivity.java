package com.student.council.electionSystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.student.council.electionSystem.R;

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
                Intent intent = new Intent(welcomeActivity.this,CandiatephnoActivity.class);
                startActivity(intent);
                finish();
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
