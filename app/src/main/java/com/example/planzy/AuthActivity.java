package com.example.planzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.auth_fragment_container_view, LoginFragment.class, null)
                    .commit();
        }
    }

    public Fragment currentFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.auth_fragment_container_view);
    }

    @Override
    public void onBackPressed() {
        if(currentFragment() instanceof SignupFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_fragment_container_view, LoginFragment.class, null)
                    .commit();
        }
    }
}