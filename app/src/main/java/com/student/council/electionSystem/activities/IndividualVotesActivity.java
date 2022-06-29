package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.student.council.electionSystem.Helper.VoterAdapter;
import com.student.council.electionSystem.Model.Upload;
import com.student.council.electionSystem.Model.Vote;
import com.student.council.electionSystem.R;

public class IndividualVotesActivity extends AppCompatActivity {

    private DatabaseReference mRef;
    private RecyclerView individualRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_votes);

        mRef = FirebaseDatabase.getInstance().getReference().child("uploads");
        individualRecyclerView = findViewById(R.id.individualRecyclerView);
        individualRecyclerView.setHasFixedSize(true);
        individualRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        individualItems();
    }

    private void individualItems() {
        FirebaseRecyclerOptions<Upload> options = new FirebaseRecyclerOptions.Builder<Upload>().setQuery(mRef,Upload.class).build();

        FirebaseRecyclerAdapter<Upload,VoterAdapter> adapter = new FirebaseRecyclerAdapter<Upload, VoterAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VoterAdapter holder, int position, @NonNull Upload model) {
                holder.voterName.setText("Candidate: "+model.getName());
                holder.iVote.setText("Total votes: "+model.getVotes());
            }

            @NonNull
            @Override
            public VoterAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vote, parent, false);
                VoterAdapter holder = new VoterAdapter(view);
                return holder;
            }
        };

//        FirebaseRecyclerOptions<Vote> options = new FirebaseRecyclerOptions.Builder<Vote>()
//                .setQuery(mRef, Vote.class)
//                .build();
//        FirebaseRecyclerAdapter<Vote, VoterAdapter> adapter =
//                new FirebaseRecyclerAdapter<Vote, VoterAdapter>(options) {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    protected void onBindViewHolder(@NonNull VoterAdapter holder, int position, @NonNull Vote model) {
//                        holder.voterName.setText("Voter: "+ model.getName());
//                        holder.iVote.setText("Vote to: "+model.getParty());
//                    }
//
//                    @NonNull
//                    @Override
//                    public VoterAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vote, parent, false);
//                        VoterAdapter holder = new VoterAdapter(view);
//                        return holder;
//                    }
//                };
        individualRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}