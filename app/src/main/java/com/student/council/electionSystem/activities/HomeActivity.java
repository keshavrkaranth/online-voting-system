package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.Helper.CandidateAdapter;
import com.student.council.electionSystem.Model.Upload;
import com.student.council.electionSystem.R;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    ImageView Tick;
    Button authButton;
    private ProgressDialog LoadingBar;
    private DatabaseReference mref;
    String Phone;
    public String isVote = "0";
    TextView VoteNowTv, VotedTv,VoteNow2;
    private Toolbar toolbar;
    RelativeLayout relativeLayout;


    private RecyclerView candidateRecyclerView;
    private DatabaseReference cRef;
    RecyclerView.LayoutManager layoutManager;
    private LinearLayout homeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent i = getIntent();
        Phone = i.getStringExtra("phone");
        mref = FirebaseDatabase.getInstance().getReference();

//        Auth = (ImageView) findViewById(R.id.authenticate);
        authButton = (Button) findViewById(R.id.authenticateButton);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        VoteNow2 = (TextView)findViewById(R.id.votenowtv1);

        Tick = (ImageView) findViewById(R.id.votedicon);
        VoteNowTv = (TextView) findViewById(R.id.votenowtv);
        VotedTv = (TextView) findViewById(R.id.votedtv);

        mref.child("Users").child(Phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isVote = dataSnapshot.child("Vote").getValue().toString();
                if (isVote.equals("1")) {
                    Tick.setVisibility(View.VISIBLE);
                    VotedTv.setVisibility(View.VISIBLE);
                    VoteNowTv.setVisibility(View.INVISIBLE);
                    VoteNow2.setVisibility(View.INVISIBLE);
//                    Auth.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.INVISIBLE);
                    authButton.setVisibility(View.INVISIBLE);

                } else {
                    Tick.setVisibility(View.INVISIBLE);
                    VotedTv.setVisibility(View.INVISIBLE);
                    VoteNowTv.setVisibility(View.VISIBLE);
                    VoteNow2.setVisibility(View.VISIBLE);
//                    Auth.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    authButton.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        LoadingBar = new ProgressDialog(this);


//        final Executor executor = Executors.newSingleThreadExecutor();
//
//
//        final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
//                .setTitle("Unlock OVoter")
//                .setSubtitle("")
//                .setDescription("place you finger to unloak")
//                .setNegativeButton("Cancel", executor, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                }).build();

        final HomeActivity activity = this;

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, VerifiyId.class);
                i.putExtra("phone", Phone);
                startActivity(i);
                finish();
            }
        });


        cRef = FirebaseDatabase.getInstance().getReference().child("uploads");
        candidateRecyclerView = findViewById(R.id.candidateRecyclerView);
        candidateRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        candidateRecyclerView.setLayoutManager(layoutManager);

        homeProgressBar = findViewById(R.id.home_progree_bar);
        homeProgressBar.setVisibility(View.VISIBLE);

        candidateList();

    }

    private void candidateList() {
        FirebaseRecyclerOptions<Upload> options = new FirebaseRecyclerOptions.Builder<Upload>()
                .setQuery(cRef, Upload.class)
                .build();

        FirebaseRecyclerAdapter<Upload, CandidateAdapter> adapter = new FirebaseRecyclerAdapter<Upload, CandidateAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CandidateAdapter holder, int position, @NonNull Upload model) {

                homeProgressBar.setVisibility(View.INVISIBLE);

                holder.candidateName.setText(model.getName());
                holder.candidateParty.setText(model.getCategory());
                Picasso.get().load(model.getImageUrl()).into(holder.candidateImage);

            }

            @NonNull
            @Override
            public CandidateAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_layout,parent,false);
                CandidateAdapter holder = new CandidateAdapter(view);
                return holder;
            }
        };

        candidateRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout2:

                Intent intent = new Intent(HomeActivity.this, welcomeActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.updatepassword2:
                Intent intent2 = new Intent(HomeActivity.this, UserUpdatePassword.class);
                intent2.putExtra("phone", Phone);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
