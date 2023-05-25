package com.example.planzy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.grpc.okhttp.internal.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPlanFragment extends Fragment {
    View view;
    Button dateBtn, timeBtn, addParticipantsBtn, createPlanBtn;
    EditText planNameTxt, planLocationTxt;
    String selectedDate = "", selectedTime = "";

    List<String> participants;

    public NewPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPlanFragment newInstance(String param1, String param2) {
        NewPlanFragment fragment = new NewPlanFragment();
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
        view = inflater.inflate(R.layout.fragment_new_plan, container, false);

        //set fragments to navigate to
        MainMenuFragment mainMenuFragment = new MainMenuFragment();

        //get ui components
        planNameTxt = (EditText) view.findViewById(R.id.planNameTxt);
        planLocationTxt = (EditText) view.findViewById(R.id.planLocationTxt);
        dateBtn = (Button) view.findViewById(R.id.dateBtn);
        timeBtn = (Button) view.findViewById(R.id.timeBtn);
        addParticipantsBtn = (Button) view.findViewById(R.id.addParticipantsBtn);
        createPlanBtn = (Button) view.findViewById(R.id.createPlanBtn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                dateBtn.setText(selectedDate);
                            }
                        },
                        year, month, date
                );

                datePickerDialog.show();
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        selectedTime = hourOfDay + ":" + minute;
                        timeBtn.setText(selectedTime);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        ActivityResultLauncher<Intent> GetParticipantsActivity =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        (result) -> {
                            // Validity checks
                            if (Activity.RESULT_OK != result.getResultCode()) {
                                Utility.showToast(getContext(), "Result Code not OK!");
                                return;
                            }
                            Intent intent = result.getData();
                            if (intent == null){
                                Utility.showToast(getContext(), "No Intent Returned!");
                                return;
                            }
                            if (!intent.hasExtra("selectedContacts")){
                                Utility.showToast(getContext(), "No Participants Added");
                                return;
                            }
                            // Valid result returned
                            ArrayList<String> returnedContacts =
                                    (ArrayList<String>) intent.getSerializableExtra("selectedContacts");
                            participants = returnedContacts;
                        });

        addParticipantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("addParticipantsBtnClick: ","Launching Add Participants Module...");
                Intent intent = new Intent(getContext(), MultiListViewActivity.class);
                GetParticipantsActivity.launch(intent);
            }
        });

        createPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String planName = planNameTxt.getText().toString();
                String planLocation = planLocationTxt.getText().toString();

                if(planName.isEmpty()){
                    planNameTxt.setError("Name is required");
                    return;
                }

                //get current user details
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if(currentUser != null){
                    FirebaseFirestore.getInstance().collection("User")
                            .document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        //get username for current user
                                        String currentUserName = task.getResult().get("username").toString();
                                        String currentUserContact = task.getResult().get("contact").toString();
                                        participants.add(currentUserContact);
                                        //Firestore does not accept ArrayLists so String[] has to converted to List<String>
                                        //to be pushed to Firestore
                                        //String[] participantsArray = new String[1];
                                        //participantsArray[0] = currentUserName;
//use the method Ref.document(*documentPath*).update(*fieldname*, FieldValue.arrayUnion(*String*)) to add new values to the List.
                                        //String[] participantsArray = task.getResult().getString("username").split("\\s*,\\s*");
                                        //List<String> participants = Arrays.asList(participantsArray);

                                        PlanModel newPlan = new PlanModel(planName, planLocation, selectedDate,
                                                                        selectedTime, currentUserName, participants);
                                        savePlanToFirebase(newPlan, mainMenuFragment);
                                    }
                                }
                            });
                } else {
                    Utility.showToast(getContext(), "An error occurred while creating the plan");
                }
            }
        });
        return view;
    }

    public void onReenter(Intent data) {
        // Do whatever with the data here
        Log.d("Re-Enter Method: ",data.toString());
    }

    void savePlanToFirebase(PlanModel newPlan, MainMenuFragment mainMenuFragment){
        DocumentReference documentReference;
        documentReference = Utility.getCollectioReferenceForPlans().document();

        documentReference.set(newPlan)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Utility.showToast(getContext(), "plan created successfully!");
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.homepage_fragment_view, mainMenuFragment)
                                    .commit();
                        } else {
                            Utility.showToast(getContext(), "plan creation failed!");
                        }
                    }
                });
    }
}