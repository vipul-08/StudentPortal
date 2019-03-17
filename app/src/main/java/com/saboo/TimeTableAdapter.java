package com.saboo;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder> {

    Context context;
    List<TimeTableModel> list;
    ArrayList<String> teachers;
    ArrayList<String> teachersId;
    ArrayAdapter spinnerArrayAdapter;

    String selectedName;

    public TimeTableAdapter(Context context, List<TimeTableModel> list) {
        this.context = context;
        this.list = list;
        teachersId = new ArrayList<>();
        teachers = new ArrayList<>();
        teachers.add("Select Teacher");
        teachersId.add("NULL");

        FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("DATAS1", "onDataChange: "+dataSnapshot);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    teachers.add(dataSnapshot1.child("fullName").getValue(String.class));
                    teachersId.add(dataSnapshot1.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_timetable_row, viewGroup, false);
        return new TimeTableAdapter.TimeTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeTableViewHolder timeTableViewHolder, int i) {
        final TimeTableModel model = list.get(i);
        Log.d("BINDS", "onBindViewHolder: times");
        spinnerArrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_dropdown_item , teachers);
        timeTableViewHolder.editText.setText(model.getSubject());
        timeTableViewHolder.spinner.setAdapter(spinnerArrayAdapter);
        Log.d("VALS1",teachers+"");
        Log.d("VALS2",teachersId+"");
        int sel = 0;
        for (int j = 0 ; j < teachersId.size() ; j++) {
            Log.d("VALS4",teachersId.get(j)+" -- "+model.getTeacher());
            if (teachersId.get(j).equals(model.getTeacher())) {
                sel = j;
                Log.d("VALS3",sel+"");
                break;
            }
        }
        timeTableViewHolder.time.setText(model.getTime());
        timeTableViewHolder.spinner.setSelection(sel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        EditText editText;
        Spinner spinner;

        public TimeTableViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.single_item_editText_subject);
            spinner = itemView.findViewById(R.id.single_item_spinner);
            time = itemView.findViewById(R.id.single_time);
        }
    }
}
