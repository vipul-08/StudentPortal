package com.saboo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;

public class FragmentDoubt extends Fragment {

    RecyclerView recyclerView;
    Button mSubmitDoubt;
    DoubtsAdapter adapter;
    ArrayList<DoubtModal> list = new ArrayList<>();
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doubt,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        mSubmitDoubt = rootView.findViewById(R.id.submitDoubt);
        final EditText doubt = rootView.findViewById(R.id.doubt);

        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new DoubtsAdapter(rootView.getContext(),list,getActivity());
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().child("Doubts");

//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//        Date d = new Date();
//        String dayOfTheWeek = sdf.format(d);
//        Log.d("DayValue",dayOfTheWeek+"");

        reference.orderByChild("timestamp").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("NewChild",""+dataSnapshot);
                DoubtModal dt = dataSnapshot.getValue(DoubtModal.class);
                list.add(dt);
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

        mSubmitDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dId= FirebaseDatabase.getInstance().getReference().child("Doubt").push().getKey();
                HashMap<String,Object>map = new HashMap<>();
                map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                map.put("timestamp", ServerValue.TIMESTAMP);
                map.put("text",doubt.getText().toString());
                map.put("solved",false);
                FirebaseDatabase.getInstance().getReference().child("Doubts").child(dId).setValue(map);
                doubt.setText("");
                doubt.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        return rootView;
    }

}