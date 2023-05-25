package com.example.planzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class HomeActivity extends AppCompatActivity {

    Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //call the first fragment
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.homepage_fragment_view, MainMenuFragment.class, null)
                    .commit();
        }
    }

    public Fragment currentFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.homepage_fragment_view);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        NewPlanFragment newPlanfragment = (NewPlanFragment) getSupportFragmentManager().findFragmentByTag("newPlanFragmentTag");
        if (newPlanfragment != null) {
            newPlanfragment.onReenter(data);
        }
    }

    @Override
    public void onBackPressed() {
        if(currentFragment() instanceof NewPlanFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homepage_fragment_view, MainMenuFragment.class, null)
                    .commit();
        }

        if(currentFragment() instanceof upcomingPlansFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homepage_fragment_view, MainMenuFragment.class, null)
                    .commit();
        }

        if(currentFragment() instanceof PlanDetailsFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homepage_fragment_view, MainMenuFragment.class, null)
                    .commit();
        }

        if(currentFragment() instanceof MainMenuFragment){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Utility.showToast(getApplicationContext(), "click back again to logout");

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
}