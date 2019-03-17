package com.saboo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class DoubtsAdapter extends RecyclerView.Adapter<DoubtsAdapter.DoubtsViewHolder> {

    private Context context;
    private List<DoubtModal> doubtsList;
    private Activity activity;

    public DoubtsAdapter(Context context, List<DoubtModal> doubtsList, Activity activity) {
        this.context = context;
        this.doubtsList = doubtsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public DoubtsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_doubt_row, viewGroup, false);
        return new DoubtsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoubtsViewHolder doubtsViewHolder, int i) {
        final DoubtModal doubtModalCurr = doubtsList.get(i);
        doubtsViewHolder.singleDoubt.setText(doubtModalCurr.getText());
        doubtsViewHolder.singleDoubtSolved.setText(doubtModalCurr.isSolved() ? "Solved" : "Unsolved");

    }

    @Override
    public int getItemCount() {
        return doubtsList.size();
    }

    public class DoubtsViewHolder extends RecyclerView.ViewHolder {

        TextView singleDoubt,singleDoubtSolved;
        public DoubtsViewHolder(@NonNull View itemView) {
            super(itemView);
            singleDoubt = itemView.findViewById(R.id.single_doubt_item);
            singleDoubtSolved = itemView.findViewById(R.id.slved_single_item);
        }
    }
}
