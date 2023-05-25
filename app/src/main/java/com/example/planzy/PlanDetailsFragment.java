package com.example.planzy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanDetailsFragment extends Fragment {

    View view;

    TextView titleText;
    public PlanDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanDetailsFragment newInstance(String param1, String param2) {
        PlanDetailsFragment fragment = new PlanDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        view = inflater.inflate(R.layout.fragment_plan_details, container, false);

        titleText = (TextView) view.findViewById(R.id.planDetailsTitle);

        getParentFragmentManager().setFragmentResultListener("dataFromSample", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String receivedData = result.getString("df1");
                titleText.setText(receivedData);
            }
        });

        return view;
    }
}