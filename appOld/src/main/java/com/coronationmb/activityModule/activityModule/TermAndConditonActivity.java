package com.coronationmb.activityModule.activityModule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.coronationmb.service.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coronationmb.R;

public class TermAndConditonActivity extends AppCompatActivity {


    @BindView(R.id.accept)
    Button accept;


    @BindView(R.id.decline)
    Button decline;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_term_and_conditon);
        ButterKnife.bind(this);

        context= TermAndConditonActivity.this;

    }

    @OnClick({R.id.accept,R.id.decline})
    public void termsButton(View view){
        int id=view.getId();

        switch (id){

            case R.id.accept:

                SharedPref.setAppAgreement(context,1);
                startActivity(new Intent(context,OnboardingActivity.class));

                break;


            case R.id.decline:
                finish();
                break;


        }

    }

}
