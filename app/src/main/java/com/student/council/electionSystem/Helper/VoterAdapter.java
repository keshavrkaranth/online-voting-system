package com.student.council.electionSystem.Helper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.council.electionSystem.R;

public class VoterAdapter extends RecyclerView.ViewHolder{

    public TextView voterName, iVote;
    private final Context context;

    public VoterAdapter(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();

        voterName = (TextView) itemView.findViewById(R.id.voterName);
        iVote = (TextView) itemView.findViewById(R.id.vote);

    }
}
