package com.example.planzy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {

    View view;

    MaterialCardView newplanCard, upcomingplansCard, historyCard;

    public MainMenuFragment() {
        // Required empty public constructor
    }
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
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
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //get ui components
        newplanCard = (MaterialCardView) view.findViewById(R.id.newplanCard);
        upcomingplansCard = (MaterialCardView) view.findViewById(R.id.upcomingplansCard);
        historyCard = (MaterialCardView) view.findViewById(R.id.historyCard);

        //get fragments to navigate to
        NewPlanFragment newPlanFragment = new NewPlanFragment();
        upcomingPlansFragment upcomingPlansFragment = new upcomingPlansFragment();

        //set onclick functions
        newplanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "create a new plan", Toast.LENGTH_SHORT).show();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.homepage_fragment_view,newPlanFragment, "newPlanFragmentTag").commit();
            }
        });

        upcomingplansCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "check upcoming plans", Toast.LENGTH_SHORT).show();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.homepage_fragment_view,upcomingPlansFragment).commit();
            }
        });

        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "check previous plans", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}