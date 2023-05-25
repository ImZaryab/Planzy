package com.example.planzy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Splash Screen Timeout
    private static final int SPLASH_TIME_OUT = 5000;

    //Animations
    Animation logoAnimation;

    //Hooks
    TextView planzyTxt;
    ImageView planzyImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.slidefrombottom);

        //Hooks
        planzyTxt = (TextView) findViewById(R.id.planzytext);
        planzyImg = (ImageView) findViewById(R.id.planzyLogo);

        //Set Animations
        planzyImg.setAnimation(logoAnimation);
        planzyTxt.setAnimation(logoAnimation);

        /* Following function runs after a specified delay which is provided after
        the function to be called */
        //Call to next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}