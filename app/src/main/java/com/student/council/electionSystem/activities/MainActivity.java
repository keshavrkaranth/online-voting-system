package com.student.council.electionSystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.student.council.electionSystem.R;

public class MainActivity extends AppCompatActivity {

    private TextView welcome,welcome2,welcome3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,welcomeActivity.class);
                MainActivity.this.startActivity(i);
                MainActivity.this.finish();
            }
        },3000);

    }
}
