package com.saboo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimesLectureActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TimeTableModel> list = new ArrayList<>();
    TimeTableAdapter adapter;

    String day;
    String cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_lecture);
        recyclerView = findViewById(R.id.recyc);

        day = getIntent().getStringExtra("day");
        adapter = new TimeTableAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cls = dataSnapshot.getValue(String.class);
                Log.d("class",cls+" hai");
                FirebaseDatabase.getInstance().getReference().child("TimeTable").child(day).child(cls).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        TimeTableModel model = dataSnapshot.getValue(TimeTableModel.class);
                        String time = "";
                        switch (dataSnapshot.getKey()) {
                            case "10-11" : time = "10 AM - 11 AM"; break;
                            case "11-12" : time = "11 AM - 12 NOON"; break;
                            case "12-1" : time = "12 NOON - 1 PM"; break;
                        }
                        model.setTime(time);
                        list.add(model);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
