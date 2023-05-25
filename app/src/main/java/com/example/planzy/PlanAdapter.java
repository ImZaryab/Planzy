package com.example.planzy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PlanAdapter extends FirestoreRecyclerAdapter<PlanModel, PlanAdapter.PlanViewHolder> {
    Context context;

    public PlanAdapter(@NonNull FirestoreRecyclerOptions<PlanModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlanViewHolder holder, int position, @NonNull PlanModel planModel) {
        holder.planTitle.setText(planModel.name);
        //Need to update plan model to incorporate status variable and utilize here
        holder.planStatus.setText(planModel.location);

        holder.itemView.setOnClickListener((v) -> {
            // onClick function for each item inside mapped against the adapter
            Intent intent = new Intent(context, PlanDetailsActivity.class);
            intent.putExtra("title", planModel.name);
            intent.putExtra("location", planModel.location);
            String docID = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docID", docID);
            intent.putExtra("createdBy", planModel.createdBy);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_plan_item, parent, false);
        return new PlanViewHolder(view);
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {

        TextView planTitle, planStatus;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            planTitle = (TextView) itemView.findViewById(R.id.planTitle);
            planStatus = (TextView) itemView.findViewById(R.id.planStatus);
        }
    }
}
