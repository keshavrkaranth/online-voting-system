package com.student.council.electionSystem.Helper;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student.council.electionSystem.ExampleItemClickListener;
import com.student.council.electionSystem.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ImageAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView partyName, partCategory;
    public CircleImageView partyImage;
    public ExampleItemClickListener listner;

    public ImageAdapter(@NonNull View itemView) {
        super(itemView);

        partyImage = (CircleImageView) itemView.findViewById(R.id.image_view_upload);
        partyName = (TextView) itemView.findViewById(R.id.text_view_party);
        partCategory = (TextView)itemView.findViewById(R.id.text_view_category);

    }


    public void setOnItemClickListener(ExampleItemClickListener listener){
        this.listner = listener;
    }
    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(), false);
    }
}
