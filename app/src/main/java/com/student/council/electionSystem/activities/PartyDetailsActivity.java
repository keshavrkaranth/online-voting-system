package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.Model.Upload;
import com.student.council.electionSystem.R;
import com.squareup.picasso.Picasso;

public class PartyDetailsActivity extends AppCompatActivity {

    TextView dpartyName, dpartyCategory, dpartyDescription;
    ImageView dpartyImage;
    private Button vote;
    private DatabaseReference mref;
    String Phone, Category;
    private String partyID = "";
    private ProgressDialog LoadingBar;
    private int votes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_details);

        Phone = getIntent().getStringExtra("phone");
        Category = getIntent().getStringExtra("category");
        partyID = getIntent().getStringExtra("pid");

        dpartyName = (TextView) findViewById(R.id.d_partyName);
        dpartyCategory = (TextView) findViewById(R.id.d_partycategory);
        dpartyDescription = (TextView) findViewById(R.id.description);
        dpartyImage = (ImageView) findViewById(R.id.d_partyImage);

        vote = (Button) findViewById(R.id.voteButton);

        getParty(partyID);


        mref = FirebaseDatabase.getInstance().getReference();
        LoadingBar = new ProgressDialog(this);

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PartyDetailsActivity.this);
                builder.setTitle("Confirm your vote!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LoadingBar.setTitle("Voting Inprogress");
                        LoadingBar.setMessage("Please wait..");
                        LoadingBar.setCanceledOnTouchOutside(false);
                        LoadingBar.show();

                        votes +=1;
                        mref.child("uploads").child(partyID).child("votes").setValue(votes);
                        mref.child("Users").child(Phone).child("Party").setValue(Category).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Intent i = new Intent(PartyDetailsActivity.this, FinalActivity.class);
                                i.putExtra("phone", Phone);
                                i.putExtra("category", dpartyCategory.getText().toString());

                                startActivity(i);

                                LoadingBar.dismiss();
                                Toast.makeText(PartyDetailsActivity.this, "Your Vote is Submitted to our database..", Toast.LENGTH_LONG).show();
                            }
                        });



                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void getParty(String partyID) {
        DatabaseReference pestRef = FirebaseDatabase.getInstance().getReference().child("uploads");

        pestRef.child(partyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Upload pests = dataSnapshot.getValue(Upload.class);
                    votes = pests.getVotes();
                    System.out.println("Current vote is"+votes);

                    dpartyName.setText(pests.getName());
                    dpartyCategory.setText(pests.getCategory());
                    dpartyDescription.setText(pests.getDescription());
                    Picasso.get().load(pests.getImageUrl()).into(dpartyImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}