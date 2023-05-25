package com.example.planzy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PlanDetailsActivity extends AppCompatActivity {

    TextView planDetailsTitle;
    TextView planDetailsLocation;
    TextView planDocID;
    String detailsTitle, detailsLocation, docID;
    Button addParticipantsBtn;

    TextView participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        participants = (TextView) findViewById(R.id.participants);
        String noParticipants = "No participants added yet";
        participants.setText(noParticipants);

        planDetailsTitle = (TextView) findViewById(R.id.planDetailsTitle);
        planDetailsLocation = (TextView) findViewById(R.id.planDetailsLocation);
        planDocID = (TextView) findViewById(R.id.planDocID);
        addParticipantsBtn = (Button) findViewById(R.id.addParticipantsBtn);

        //receive data from Intent
        detailsTitle = getIntent().getStringExtra("title");
        detailsLocation = getIntent().getStringExtra("location");

        //change the variable to correct name for CreatedBy field
        docID = getIntent().getStringExtra("createdBy");

        //set the received data to appropriate components
        planDetailsTitle.setText(detailsTitle);
        planDetailsLocation.setText(detailsLocation);
        planDocID.setText(docID);

        //get the username for current user
        /* FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            FirebaseFirestore.getInstance().collection("Plans")
                    .document(currentUser.getUid()).collection("user_plans")
                            .document(docID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                planDocID.setText(task.getResult().getString("createdBy"));
                            }
                        }
                    });
        } else {
            planDocID.setText("");
        } */

        ActivityResultLauncher<Intent> GetParticipantsActivity =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        (result) -> {
                            // Validity checks
                            if (Activity.RESULT_OK != result.getResultCode()) {
                                Utility.showToast(this, "Result Code not OK!");
                                return;
                            }
                            Intent intent = result.getData();
                            if (intent == null){
                                Utility.showToast(this, "No Intent Returned!");
                                return;
                            }
                            if (!intent.hasExtra("selectedContacts")){
                                Utility.showToast(this, "No Participants Added");
                                return;
                            }
                            // Valid result returned
                            ArrayList<String> returnedContacts =
                                    (ArrayList<String>) intent.getSerializableExtra("selectedContacts");

                            if(returnedContacts != null){
                                participants.setText(String.valueOf(returnedContacts));
                            } else {
                                String nullReturn = "returnedContacts is null";
                                participants.setText(nullReturn);
                            }
                        });

        addParticipantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MultiListViewActivity.class);
                GetParticipantsActivity.launch(intent);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}