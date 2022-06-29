package com.student.council.electionSystem.Helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.council.electionSystem.ExampleItemClickListener;
import com.student.council.electionSystem.R;

public class CandidateAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView candidateName, candidateParty;
    public ImageView candidateImage;
    public ExampleItemClickListener listner;

    public CandidateAdapter(@NonNull View itemView) {
        super(itemView);
        candidateImage = (ImageView) itemView.findViewById(R.id.candidate_image);
        candidateName = (TextView) itemView.findViewById(R.id.candidate_name);
        candidateParty = (TextView)itemView.findViewById(R.id.candidate_party);
    }

    public void setOnItemClickListener(ExampleItemClickListener listener){
        this.listner = listener;
    }
    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(), false);
    }
}
