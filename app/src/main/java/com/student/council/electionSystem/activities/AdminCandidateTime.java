package com.student.council.electionSystem.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.council.electionSystem.R;

public class AdminCandidateTime extends AppCompatActivity {
    EditText date_time_start,date_time_end;
    Button set_time_btn;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_candidate_time);
        mref= FirebaseDatabase.getInstance().getReference();

        date_time_start=findViewById(R.id.date_time_start);
        date_time_end=findViewById(R.id.date_time_end);

        set_time_btn = findViewById(R.id.settimebtnc);

        date_time_start.setInputType(InputType.TYPE_NULL);
        date_time_end.setInputType(InputType.TYPE_NULL);

        date_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(date_time_start);
            }

            private void showDateTimeDialog(EditText date_time_start) {
                final Calendar calendar=Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                    calendar.set(Calendar.MINUTE,minute);
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");
                                date_time_start.setText(simpleDateFormat.format(calendar.getTime()));


                            }
                        };
                        new TimePickerDialog(AdminCandidateTime.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

                    }
                };
                new DatePickerDialog(AdminCandidateTime.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        date_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(date_time_end);
            }

            private void showDateTimeDialog(EditText date_time_end) {
                final Calendar calendar1=Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int dayOfMonth1) {
                        calendar1.set(Calendar.YEAR,year1);
                        calendar1.set(Calendar.MONTH,month1);
                        calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth1);

                        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay1, int minute1) {
                                calendar1.set(Calendar.HOUR_OF_DAY,hourOfDay1);
                                calendar1.set(Calendar.MINUTE,minute1);
                                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yy-MM-dd HH:mm");
                                date_time_end.setText(simpleDateFormat1.format(calendar1.getTime()));


                            }
                        };
                        new TimePickerDialog(AdminCandidateTime.this,timeSetListener,calendar1.get(Calendar.HOUR_OF_DAY),calendar1.get(Calendar.MINUTE),false).show();

                    }
                };
                new DatePickerDialog(AdminCandidateTime.this,dateSetListener,calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        set_time_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                System.out.println("Start date and time is"+date_time_start.getText().toString());
                System.out.println("End date and time is"+date_time_end.getText().toString());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
                LocalDateTime start = LocalDateTime.parse(date_time_start.getText().toString(),formatter);
                LocalDateTime end = LocalDateTime.parse(date_time_end.getText().toString(),formatter);

                if (end.compareTo(start)<0)
                {
                    Toast.makeText(AdminCandidateTime.this,"Please check your date and time",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference  ref = FirebaseDatabase.getInstance().getReference("candidateTiming");
                    Map<String,Object> details=new HashMap<String,Object>();

                    details.put("start_date",date_time_start.getText().toString());
                    details.put("end_date",date_time_end.getText().toString());

                    ref.setValue(details);

                }
            }
        });


    }
}