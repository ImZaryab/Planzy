package com.example.planzy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    View view;
    Button loginBtn, signupBtn;

    EditText emailTxt, passwordTxt;

    TextView forgotpasswordTxt;

    ProgressDialog progressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // set firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //progressDialog
        progressDialog = new ProgressDialog(getContext());

        //get ui components
        loginBtn = (Button) view.findViewById(R.id.loginBtn);
        signupBtn = (Button) view.findViewById(R.id.signupBtn);
        emailTxt = (EditText) view.findViewById(R.id.emailField);
        passwordTxt = (EditText) view.findViewById(R.id.passwordField);
        forgotpasswordTxt = (TextView) view.findViewById(R.id.forgotPassword);
        Bundle bundle = new Bundle();
        SignupFragment signupFragment = new SignupFragment();

        //set onclick functions
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    Toast.makeText(getActivity(), "login successful!", Toast.LENGTH_SHORT).show();
                                    Intent sendToHomepage = new Intent(getContext(), HomeActivity.class);
                                    startActivity(sendToHomepage);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Utility.showToast(getContext(), "all fields are required");
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.auth_fragment_container_view, signupFragment).commit();
            }
        });

        forgotpasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString();
                progressDialog.setTitle("sending password reset mail");
                progressDialog.show();

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(getActivity(), "password reset email sent!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}