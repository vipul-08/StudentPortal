package com.saboo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentProfile extends Fragment {
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private TextView username;
    private TextView fullname;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_profile,container,false);
        mDatabase1= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName");
        username=rootView.findViewById(R.id.user_name);
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fullName");
        fullname=rootView.findViewById(R.id.full_name);
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue().toString();
                username.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name1=dataSnapshot.getValue().toString();
                fullname.setText(name1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }
}