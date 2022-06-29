package com.student.council.electionSystem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.council.electionSystem.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CandidateRegistration extends AppCompatActivity {
    private EditText Name,Usn,Branch,Description;
    private Button Submit;
    private DatabaseReference mref;
    private ProgressDialog LoadingBar;
    private ImageView profilePic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String productRandomKey, downloadImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_registration);

        Name=(EditText)findViewById(R.id.nameentry);
        Usn=(EditText)findViewById(R.id.usneentry);
        Branch=(EditText)findViewById(R.id.branchentry);
        Description=(EditText)findViewById(R.id.descriptionentry);
        Submit=(Button)findViewById(R.id.submitbutton);
        mref= FirebaseDatabase.getInstance().getReference();
        LoadingBar=new ProgressDialog(this);
        profilePic = findViewById(R.id.profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Name.getText().toString()))
                {
                    Toast.makeText(CandidateRegistration.this, "Please enter your Name...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Usn.getText().toString()))
                {
                    Toast.makeText(CandidateRegistration.this, "Please enter your USN...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Branch.getText().toString()))
                {
                    Toast.makeText(CandidateRegistration.this, "Please enter your Branch...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Description.getText().toString()))
                {
                    Toast.makeText(CandidateRegistration.this, "Enter details about your work experience...", Toast.LENGTH_SHORT).show();
                }
                else if(!validateUSN(Usn.getText().toString()))
                {
                    Toast.makeText(CandidateRegistration.this, "Please enter a valid ID number", Toast.LENGTH_SHORT).show();
                }
                else{
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!(dataSnapshot.child("Uploads").child(Usn.getText().toString()).exists())) //"+91"+
                            {
                                CreateAccount(Name,Usn,Branch,Description);
                            }
                            else
                            {
                                Toast.makeText(CandidateRegistration.this, "Account with this USN already exist..", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            private boolean validateUSN(String input) {
                Pattern p = Pattern.compile("^4(cb|CB)(18|19|20|21)(cs|ec|is|me|CS|EC|IS|ME)(0|1)[0-9][0-9]");
                Matcher m = p.matcher(input);
                return m.matches();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(CandidateRegistration.this,"cannot return back",Toast.LENGTH_SHORT).show();
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();

        }
    }

    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference reference = storageReference.child("images/"+getIntent().getStringExtra("mobile")+".jpg");
        final UploadTask uploadTask = reference.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(CandidateRegistration.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CandidateRegistration.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri>uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = reference.getDownloadUrl().toString();

                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(CandidateRegistration.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });




    }

    private void CreateAccount(EditText name, EditText usn, EditText branch, EditText description) {
        Map<String,Object> details=new HashMap<String,Object>();
        details.put("name",name.getText().toString());
        details.put("pid",usn.getText().toString());
        details.put("category",branch.getText().toString());
        details.put("description",description.getText().toString());
        details.put("phone",getIntent().getStringExtra("mobile"));
        details.put("votes",0);
        details.put("imageUrl",downloadImageUrl);

        DatabaseReference  ref = FirebaseDatabase.getInstance().getReference("uploads/"+usn.getText().toString());
        ref.setValue(details);

        Intent intent = new Intent(CandidateRegistration.this,welcomeActivity.class);
        startActivity(intent);
        finish();

        

    }
}