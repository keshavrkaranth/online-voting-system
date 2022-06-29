package com.student.council.electionSystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.student.council.electionSystem.R;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }

    public void backtowelcome(View view) {
        startActivity(new Intent(AboutAppActivity.this, welcomeActivity.class));
    }
}