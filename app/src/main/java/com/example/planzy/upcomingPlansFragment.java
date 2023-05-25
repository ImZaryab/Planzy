package com.example.planzy;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link upcomingPlansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class upcomingPlansFragment extends Fragment {

    View view;
    RecyclerView plansRecyclerView;
    PlanAdapter planAdapter;
    public upcomingPlansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upcomingPlansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static upcomingPlansFragment newInstance(String param1, String param2) {
        upcomingPlansFragment fragment = new upcomingPlansFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming_plans, container, false);

        //get ui components
        plansRecyclerView = (RecyclerView) view.findViewById(R.id.plansRecyclerView);

        setupRecyclerView();
        return view;
    }

    void setupRecyclerView(){
        Query query = Utility.getCollectioReferenceForPlans();
        FirestoreRecyclerOptions<PlanModel> options = new FirestoreRecyclerOptions.Builder<PlanModel>()
                .setQuery(query, PlanModel.class).build();
        plansRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapter = new PlanAdapter(options, getContext());
        plansRecyclerView.setAdapter(planAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        planAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        planAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        planAdapter.notifyDataSetChanged();
    }
}