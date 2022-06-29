package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.student.council.electionSystem.R;

public class FinalActivity extends AppCompatActivity {

    TextView V1,V2;
    String PartyName, Phone;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_final);


        mref= FirebaseDatabase.getInstance().getReference();
        Intent i=getIntent();
        PartyName=i.getStringExtra("category");
        Phone = i.getStringExtra("phone");

        V1=(TextView)findViewById(R.id.v1);
        V2=(TextView)findViewById(R.id.v2);

        V2.setText("Your vote is submitted to "+PartyName);

        mref.child("Users").child(Phone).child("Vote").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(FinalActivity.this, " ", Toast.LENGTH_LONG).show();
            }

        });


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//
//            case R.id.logout:
//
//                Intent intent=new Intent(FinalActivity.this,welcomeActivity.class);
//                startActivity(intent);
//                return true;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onBackPressed() {

        Toast.makeText(FinalActivity.this,"cannot return back",Toast.LENGTH_SHORT).show();
    }

    public void logout(View view) {
        Intent intent=new Intent(FinalActivity.this,welcomeActivity.class);
        startActivity(intent);
    }
}
