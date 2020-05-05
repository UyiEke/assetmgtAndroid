package com.coronationmb.activityModule.activityModule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import com.coronationmb.R;
import com.coronationmb.service.SharedPref;

public class SplashScreenActivity extends AppCompatActivity {
    private static  final long SPLASH_TIME_OUT=3000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context=SplashScreenActivity.this;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(SharedPref.getAppAgreement(context)==1){
                    startActivity(new Intent(context,OnboardingActivity.class));
                }else {
                    startActivity(new Intent(context,TermAndConditonActivity.class));
                }

            }
        },SPLASH_TIME_OUT);
    }





}
