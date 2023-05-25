package com.example.planzy;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    View view;
    Button goToLoginBtn, signupBtn;

    EditText emailTxt, usernameTxt, contactTxt, passwordTxt, repasswordTxt;

    ProgressDialog progressDialog;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        //set firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //set firestore instance
        firestore = FirebaseFirestore.getInstance();

        //get ui components
        signupBtn = (Button) view.findViewById(R.id.signupBtn);
        goToLoginBtn = (Button) view.findViewById(R.id.loginBtn);
        emailTxt = (EditText) view.findViewById(R.id.emailField);
        usernameTxt = (EditText) view.findViewById(R.id.usernameField);
        passwordTxt = (EditText) view.findViewById(R.id.passwordField);
        contactTxt = (EditText) view.findViewById(R.id.contactField);
        repasswordTxt = (EditText) view.findViewById(R.id.repasswordField);
        progressDialog = new ProgressDialog(getContext());
        Bundle bundle = new Bundle();
        LoginFragment loginFragment = new LoginFragment();

        //set onclick functions
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString();
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String contact = contactTxt.getText().toString();
                String repassword = repasswordTxt.getText().toString();

                if(password.equals(repassword)){
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //On successful account creation
                                    //Show Toast Message
                                    Toast.makeText(getActivity(), "account creation successful", Toast.LENGTH_SHORT).show();

                                    //Save User Details to Firestore
                                    firestore.collection("User")
                                            .document(firebaseAuth.getUid())
                                            .set(new UserModel(username, email, contact));

                                    //Switch to Login fragment
                                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                                    fm.replace(R.id.auth_fragment_container_view,loginFragment).commit();

                                    progressDialog.cancel();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //On failure
                                    //Show Toast Message
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                    progressDialog.cancel();
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "gotologin button clicked", Toast.LENGTH_SHORT).show();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.auth_fragment_container_view,loginFragment).commit();
            }
        });

        return view;
    }
}